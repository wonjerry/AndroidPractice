package org.androidtown.stationgraphtest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.androidtown.stationgraphtest.StationGraphVO.Identifier;

public class StationGraph extends AbstractGraph<StationGraphVO> {
	
	private static StationGraph instance = null;
	
	public static StationGraph getInstance() {
		if(instance == null) {
			synchronized (StationGraph.class) {
				if(instance == null)
					instance = build();
			}
		}
		
		return instance;
	}
	
	private StationGraph() {
		super();
		graphType = GraphType.UNDIRECTED;
	}
	
	private static StationGraph build() {
		
		try {
			StationGraph stationGraph = new StationGraph();
			
			for(Field field : Model.class.getDeclaredFields()) {
				String lineNum = field.getName().replace("line", "");
				String[] stationNames = ((String) field.get("java.lang.String")).split(" ");
				
				for (int i = 0; i < stationNames.length - 1; i++) {
					String stationName1 = stationNames[i];
					String stationName2 = stationNames[i + 1];

					StationGraphVO vo1 = new StationGraphVO(stationName1, lineNum, Identifier.CURRENT);
					stationGraph.addVertex(vo1);

					StationGraphVO vo2 = new StationGraphVO(stationName2, lineNum, Identifier.CURRENT);
					stationGraph.addVertex(vo2);

					stationGraph.addEdge(vo1, vo2);
				}
			}
			
			filter(stationGraph);
			return stationGraph;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 예외처리. 이전/다음역이 여러개 인 경우 처리와 이어지지 않은 노선에 대한 삭제.
	 * @param graph
	 */
	private static void filter(StationGraph graph) {
		/* 1호선 */
		// 구로 & 인천
		setSubLine(graph, "구로", "가산디지털단지", "1");
		graph.removeEdge("구로", "인천", "1");
		graph.removeEdge("인천", "구로", "1");

		// 금천구청 & 광명
		setSubLine(graph, "금천구청", "광명", "1");
		graph.removeEdge("금천구청", "신창", "1");
		graph.removeEdge("광명", "병점", "1");

		// 병점
		setSubLine(graph, "병점", "서동탄", "1");
		graph.removeEdge("병점", "광명", "1");

		/* 2호선 */
		// 신도림 & 신설동
		setSubLine(graph, "신도림", "도림천", "2");
		graph.removeEdge("신도림", "신설동", "2");
		graph.removeEdge("신설동", "신도림", "2");

		// 성수
		setSubLine(graph, "성수", "용답", "2");
		graph.removeEdge("성수", "시청", "2");
		graph.removeEdge("시청", "성수", "2");

		/* 5호선 */
		// 강동 상일동
		setSubLine(graph, "강동", "둔촌동", "5");
		graph.removeEdge("강동", "상일동", "5");
		graph.removeEdge("상일동", "강동", "5");

		/* 6호선 */
		graph.addEdge(new StationGraphVO("구산", "6", Identifier.CURRENT), new StationGraphVO("응암", "6", Identifier.NEXT)); // 환형
		setSubLine(graph, "응암", "구산", "6");

		/* 경의중앙선 */
		setSubLine(graph, "가좌", "신촌", "K");
		graph.removeEdge("가좌", "지평", "K");
	}

	private static void setSubLine(StationGraph graph, String fromStationName, String toStationName, String lineNum) {
		for(Object e : graph.getEdges(fromStationName)) {
			if(e.getToVertex().getStationName().equals(toStationName) && e.getToVertex().getLineNum().equals(lineNum)) {
				e.getToVertex().setMainLine(false);
				break;
			}
		}
	}
	
	private String[] lineNums = {"A", "B", "E", "G", "I", "I2", "K", "KK", "S", "SU", "U"};
	
	private boolean checkLineNum(String lineNum) {
		boolean result = false;
		
		try {
			int lineNum1 = Integer.parseInt(lineNum);
			return 1 <= lineNum1 && lineNum1 <= 9;
		} catch (Exception e) {
			for(String lineNum1 : lineNums) {
				if(lineNum1.equals(lineNum)) {
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	public void removeEdge(String fromStationName, String toStationName, String lineNum) {
		LinkedList<Object> edges = edgesByVertices.get(new StationGraphVO(fromStationName, lineNum, Identifier.CURRENT));

		int result = Integer.MIN_VALUE;
		
		for(int i=0 ; i<edges.size() ; i++) {
			Object e = edges.get(i);
			
			if(e.toVertex.getStationName().equals(toStationName) && e.toVertex.getLineNum().equals(lineNum)) {
				result = i;
				break;
			}			
		}
		
		if(result < 0)
			throw new NullPointerException("Not found");
		
		edges.remove(result);
	}
	
	@Override
	public void addVertex(StationGraphVO... vertice) {
		for(StationGraphVO vertex : vertice) {
			
			//이미 vertex가 존재하는가?
			if(edgesByVertices.containsKey(vertex))
				continue;
				
			// Key와 Value가 완전히 참조가 분리되도록 깊은 복사
			StationGraphVO vertexCopy = deepCopy(vertex);
			vertexCopy.setIdentifier(Identifier.CURRENT);
			edgesByVertices.put(vertexCopy, new LinkedList<>());
		}
	}
	
	// 일반 간선 추가
	public void addEdge(StationGraphVO fromVertex, StationGraphVO... toVertice) {
		if (!edgesByVertices.containsKey(fromVertex))
			throw new NullPointerException("The fromVertex is not exists.");

		for(StationGraphVO toVertex : toVertice) {
			if (!edgesByVertices.containsKey(toVertex))
				throw new NullPointerException("The toVertex is not exists.");
			
			toVertex.setIdentifier(Identifier.NEXT);
			fromVertex.setIdentifier(Identifier.PREVIOUS);
			
			
			LinkedList<Object> edges = edgesByVertices.get(fromVertex);
			Edge newEdge = new Edge(fromVertex, toVertex);
			// 중복 추가 방지 (정점 하나에 이어진 정점이 2개 이상 포함될 수가 없으므로)
			if(edges.contains(newEdge))
				return;
			
			edges.add(newEdge);
			
			// 무방향 그래프 대칭 처리
			if (graphType == GraphType.UNDIRECTED)
				addVertexForUndirectGraph(toVertex, fromVertex);
		}

		// fromVertex만 현재역으로 변환
		for(LinkedList<Object> vertex : edgesByVertices.values()) {
			for(Object edge : vertex) {
				edge.fromVertex.setIdentifier(Identifier.CURRENT);
			}
		}
	}

	// 무방향 그래프 대칭 처리
	private void addVertexForUndirectGraph(StationGraphVO toVertex, StationGraphVO fromVertex) {

		// 참조 문제로 인한 Bug 해결
		StationGraphVO toVertexCopy = deepCopy(toVertex);
		StationGraphVO fromVertexCopy = deepCopy(fromVertex);
		
		if (!edgesByVertices.containsKey(toVertexCopy))
			edgesByVertices.put(toVertexCopy, new LinkedList<>());

		fromVertex.setIdentifier(Identifier.PREVIOUS);
		toVertex.setIdentifier(Identifier.NEXT);
		
		LinkedList<Object> edges = edgesByVertices.get(toVertexCopy);
		edges.add(new Edge(toVertexCopy, fromVertexCopy));
	}
	
	public LinkedList<Object> getEdges(String stationName) {
		return super.getEdges(new StationGraphVO(stationName, "", Identifier.CURRENT));
	}
	
	@Override
	public LinkedList<StationGraphVO> travelDFS(StationGraphVO vertex, boolean isAscending) {
		if(!checkLineNum(vertex.getLineNum()))
			throw new IllegalArgumentException("Unavailable lineNum");
		
		//String lineNum = vertex.getLineNum();
		
		LinkedList<StationGraphVO> results = new LinkedList<>();
		
		HashSet<StationGraphVO> checkVisitSet = new HashSet<>();
		//HashSet<String> checkVisitSet = new HashSet<>();
		
		LinkedList<StationGraphVO> stack = new LinkedList<>();

		//첫 번째 Node 방문
		//StationVO firstVertex = getVertex(vertex);
		StationGraphVO firstVertex = vertex;
		stack.push(firstVertex);
		checkVisitSet.add(firstVertex);
		
		while(!stack.isEmpty()) {
			StationGraphVO poppedVertex = stack.pop();
			results.add(poppedVertex);
			
			sort(edgesByVertices.get(poppedVertex), !isAscending);
			
			for(Object edge : edgesByVertices.get(poppedVertex)) {

				StationGraphVO linkedVertex = edge.getToVertex();
				linkedVertex = edgesByVertices.ceilingKey(linkedVertex); //추가
				
				if(!checkVisitSet.contains(linkedVertex)) {
					checkVisitSet.add(linkedVertex);
					stack.push(linkedVertex);
				}
			}
		}
		
		return results;
	}
	
	@Override
	public LinkedList<StationGraphVO> travelDFS(boolean isAscending) {
		return travelDFS(edgesByVertices.firstEntry().getKey(), isAscending);
	}
	
	@Override
	public LinkedList<StationGraphVO> travelBFS(StationGraphVO vertex, boolean isAscending) {
		LinkedList<StationGraphVO> results = new LinkedList<>();
		
		HashSet<StationGraphVO> checkVisitSet = new HashSet<>();
		
		Queue<StationGraphVO> queue = new LinkedList<>();
		
		//첫 번째 Node 방문
		StationGraphVO firstVertex = getVertex(vertex);
		queue.offer(firstVertex);
		checkVisitSet.add(firstVertex);
		
		while(!queue.isEmpty()) {
			StationGraphVO dequeuedVertex = queue.poll();
			results.add(dequeuedVertex);
			
			sort(edgesByVertices.get(dequeuedVertex), isAscending);
			
			for(Object edge : edgesByVertices.get(dequeuedVertex)) {
				
				StationGraphVO linkedVertex = edge.getToVertex();
				linkedVertex = edgesByVertices.ceilingKey(linkedVertex); //추가
				
				if(!checkVisitSet.contains(linkedVertex)) {
					checkVisitSet.add(linkedVertex);
					queue.offer(linkedVertex);
				}
			}
		}
		
		return results;
	}
	
	@Override
	public LinkedList<StationGraphVO> travelBFS(boolean isAscending) {
		return travelBFS(edgesByVertices.firstEntry().getKey(), isAscending);
	}
	
	/**
	 * Wrapping.
	 * @param stationName
	 * @return
	 */
	public List<Object> get(String stationName, String lineNum) {
        try {
            return getEdges(stationName).stream().filter(edge -> edge.getToVertex().getLineNum().equals(lineNum)).map(Edge::getToVertex).collect(Collectors.toList());
            
            // 속도에 민감하다면 그냥 for 사용.
            /*ArrayList<StationGraphVO> list = new ArrayList<>();
            for(AbstractGraph<StationGraphVO>.Edge edge : getEdges(stationName)) {
                StationGraphVO toVertex = edge.getToVertex();

                if(toVertex.getLineNum().equals(lineNum)) {
                    list.add(toVertex);
                }
            }

            return list;*/
        } catch (Exception e) {
        	e.printStackTrace();
        }

        return null;
    }

	/**
	 * Wrapping.
	 * @param stationName
	 * @return
	 */
    public List<Object> get(String stationName) {
        try {
            return getEdges(stationName).stream().map(Edge::getToVertex).collect(Collectors.toList());
        } catch (Exception e) {
        	e.printStackTrace();
        }

        return null;
    }
	
	@SuppressWarnings("unchecked")
	private <T> T deepCopy(T obj) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (T)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

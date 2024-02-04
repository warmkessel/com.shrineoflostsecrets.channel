<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, java.util.function.*,com.google.cloud.datastore.*, com.shrineoflostsecrets.channel.database.datastore.*, com.shrineoflostsecrets.channel.constants.*, com.shrineoflostsecrets.channel.database.entity.*, com.shrineoflostsecrets.channel.util.*"%>
<html>
<!-- Google tag (gtag.js) -->
<script async="true"
	src="https://www.googletagmanager.com/gtag/js?id=G-N2VTBWYNCJ"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag() {
		dataLayer.push(arguments);
	}
	gtag('js', new Date());
	gtag('config', 'G-N2VTBWYNCJ');
</script>
<body>
	<table>
		<%
		String requestChannel = request.getParameter("channel");
		String channel = (requestChannel != null && !requestChannel.isEmpty()) ? requestChannel : "shrineoflostsecrets";
		%>
		<%=channel%>
		<%
		try {
			Map<Long, Long> scoreMap = new HashMap<>();

			List<Entity> listChannels = ShrineVoteService.listVotes(channel);
			for (Entity entity : listChannels) {
			    ShrineVote vote = new ShrineVote();
			    vote.loadFromEntity(entity);
			    // Use an anonymous class instead of a lambda expression
			    scoreMap.merge(vote.getShrineChannelEventId(), vote.getAmount(), new BiFunction<Long, Long, Long>() {
			        @Override
			        public Long apply(Long a, Long b) {
			            return a + b;
			        }
			    });
			}
			%>size <%=listChannels.size() %><%
			 // Convert the entry set of the map to a list
		    List<Map.Entry<Long, Long>> sortedEntries = new ArrayList<>(scoreMap.entrySet());
		    // Sort the list by values in descending order without using lambda expressions
		    Collections.sort(sortedEntries, new Comparator<Map.Entry<Long, Long>>() {
		        @Override
		        public int compare(Map.Entry<Long, Long> e1, Map.Entry<Long, Long> e2) {
		            return e2.getValue().compareTo(e1.getValue());
		        }
		    });
		    
		    
			// Printing the sorted results
			for (Map.Entry<Long, Long> entry : sortedEntries) {
				System.out.println("ID: " + entry.getKey() + ", Total Score: " + entry.getValue());
				ShrineChannelEvent channelEvent = new ShrineChannelEvent();
				channelEvent.loadEvent(entry.getKey());
		%>
		<tr>
			<td><a href="/service/voteEvent.jsp?id=<%=channelEvent.getId() %>&channel=<%=channelEvent.getTwitchChannel() %>&userName=<%=channelEvent.getTwitchUser() %>&amount=100">Vote!</a></td><td>
			<td><%=entry.getValue()%></td>
			<td><%=channelEvent.getTwitchUser()%></td>
			<td><%=channelEvent.getMessage()%></td>
		</tr>
		<%
		}
		%>
		<%
		} catch (

		Exception e) {
		// Handle exceptions appropriately
		}
		%>
	</table>
</body>
</html>


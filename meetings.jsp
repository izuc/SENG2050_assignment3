<%@ include file="includes/header.jsp" %>
<%@page import="Project.bol.Meeting"%>
<c:if test="${sessionScope.user != null}">
    <div id="content_top">
        <div id="content_buttons">
            <c:if test="${sessionScope.groupID > 0}">
                <a href="schedule_meeting.jsp" id="new_button"></a>
            </c:if>
        </div>
        <%@ include file="includes/group_selection.jsp" %>
    </div>
    <div id="content_data">
        <c:if test="${sessionScope.groupID == 0}">
            Please select a group.
        </c:if>
        <c:if test="${sessionScope.groupID > 0}">
            <table id="meetings_unconfirmed"></table>
            <div id="meetings_unconfirmed_pager"></div>
            <br />
            <table id="meetings_confirmed"></table>
            <div id="meetings_confirmed_pager"></div>
            <script type="text/javascript">
                jQuery("#meetings_unconfirmed").jqGrid({ 
                    url:'FetchMeetings?status=0&groupID=${sessionScope.groupID}', 
                    datatype: "json",
                    height: 250,
                    width: 650,
                    colNames:['Meeting ID','Location','Date & Time'], 
                    colModel:[ {name:'meeting_id',index:'meeting_id', width:50, sorttype:"int", classes: 'pointer', hidden:true}, 
                        {name:'location',index:'location', width:50, classes: 'pointer'}, 
                        {name:'datetime',index:'datetime', width:50, classes: 'pointer', hidden:true}],
                    caption: "Unconfirmed Meetings",
                    pager: '#meetings_unconfirmed_pager', 
                    sortname: 'location',
                    viewrecords: true, 
                    sortorder: "asc",
                    hoverrows:false,
                    rowNum: 10,
                    ondblClickRow: function(row) {
                        var id = jQuery("#meetings_unconfirmed").jqGrid('getCell', row, 'meeting_id');
                        document.location.href = 'view_unconfirmed_meeting.jsp?meeting_id=' + id;
                    }
                });
                jQuery("#meetings_unconfirmed").jqGrid('navGrid','#meetings_unconfirmed_pager',{edit:false,add:false,del:false,search:false});
                
                jQuery("#meetings_confirmed").jqGrid({ 
                    url:'FetchMeetings?status=1&groupID=${sessionScope.groupID}', 
                    datatype: "json",
                    height: 250,
                    width: 650,
                    colNames:['Meeting ID','Location','Date & Time'], 
                    colModel:[ {name:'meeting_id',index:'meeting_id', width:50, sorttype:"int", classes: 'pointer', hidden:true}, 
                        {name:'location',index:'location', width:50, classes: 'pointer'}, 
                        {name:'datetime',index:'datetime', width:50, classes: 'pointer'}], 
                    caption: "Confirmed Meetings",
                    pager: '#meetings_confirmed_pager', 
                    sortname: 'location',
                    viewrecords: true, 
                    sortorder: "asc",
                    hoverrows:false,
                    rowNum: 10,
                    ondblClickRow: function(row) {
                        var id = jQuery("#meetings_confirmed").jqGrid('getCell', row, 'meeting_id');
                        document.location.href = 'view_confirmed_meeting.jsp?meeting_id=' + id;
                    }
                });
                jQuery("#meetings_confirmed").jqGrid('navGrid','#meetings_confirmed_pager',{edit:false,add:false,del:false,search:false});
            </script>
        </c:if>
    </div>
</c:if>
<%@ include file="includes/footer.jsp" %>
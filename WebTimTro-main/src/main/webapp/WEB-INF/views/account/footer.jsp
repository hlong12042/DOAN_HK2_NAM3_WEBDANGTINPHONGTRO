<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<script src="resources/vendors/jquery/jquery.min.js"></script>
<script src="resources/vendors/fomantic-ui/semantic.min.js"></script>
<script src="resources/js/main.js"></script>
<!-- endinject -->
<!-- datatables:js -->
<script
	src="resources/vendors/datatables.net/js/jquery.dataTables.min.js"></script>
<script
	src="resources/vendors/datatables.net/datatables.net-se/js/dataTables.semanticui.min.js"></script>
<script
	src="resources/vendors/datatables.net/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
<script
	src="resources/vendors/datatables.net/datatables.net-responsive-se/js/responsive.semanticui.min.js"></script>
<script
	src="resources/vendors/datatables.net/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
<script
	src="resources/vendors/datatables.net/datatables.net-buttons/js/buttons.colVis.min.js"></script>
<!-- <script
	src="resources/vendors/datatables.net/datatables.net-buttons/js/buttons.html5.min.js"></script>
<script
	src="resources/vendors/datatables.net/datatables.net-buttons/js/buttons.print.min.js"></script> -->
<script
	src="resources/vendors/datatables.net/datatables.net-buttons-se/js/buttons.semanticui.min.js"></script>
<script src="resources/vendors/jszip/jszip.min.js"></script>

<!-- endinject -->
<script>
	$(document).ready(function() {
		// - MESSAGES
		$('.message .close').on('click', function() {
			$(this).closest('.message').transition('fade');
		});
		 // - DATATABLES
		$(document).ready(function() {
			$('#mytable').DataTable();
		});
		var table = $('#mytable').DataTable({
			lengthChange : false,
			buttons : [ 'colvis']
		});
		table.buttons().container().appendTo(
				$('div.eight.column:eq(0)', table.table()
						.container())); 
	});
</script>
</body>
</html>
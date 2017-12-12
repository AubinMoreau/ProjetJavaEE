<%-- 
    Document   : GraphiqueParLocalisation
    Created on : 12 déc. 2017, 17:31:17
    Author     : aubin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Ventes par catégories</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<!-- On charge l'API Google -->
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript">
		google.load("visualization", "1", {packages: ["corechart"]});

		// Après le chargement de la page, on fait l'appel AJAX
		google.setOnLoadCallback(doAjax);
		
		function drawChart(dataArray) {
			var data = google.visualization.arrayToDataTable(dataArray);
			var options = {
				title: 'Ventes par localisation',
				is3D: true,
                                width:500,
                                height:500
			};
			var chart = new google.visualization.PieChart(document.getElementById('piechart'));
			chart.draw(data, options);
		}

		// Afficher les ventes par client
		function doAjax() {
			$.ajax({
				url: "ServletGraphiqueLocalisation",
				dataType: "json",
				success: // La fonction qui traite les résultats
					function (result) {
						// On reformate le résultat comme un tableau
						var chartData = [];
						// On met le descriptif des données
						chartData.push(["Localisation", "Ventes"]);
						for(var client in result.records) {
							chartData.push([client,result.records[client]]);
						}
						// On dessine le graphique
						drawChart(chartData);
					},
				error: showError
			});
		}
		
		// Fonction qui traite les erreurs de la requête
		function showError(xhr, status, message) {
			alert("Erreur: " + status + " : " + message);
		}

	</script>
    </head>
    <body>
        <form action="ServletGraphiqueLocalisation" method="POST">   
        Date Début : <input type="date" name="dateDebut"></br>
        Date fin : <input type="date" name="datefin"></br>
        </form>
        <a href='ServletGraphiqueLocalisation' target="_blank">Voir les données brutes</a><br>
	<!-- Le graphique apparaît ici -->
	<div id="piechart" style="width: 900px; height: 500px;"></div>
    </body>
</html>

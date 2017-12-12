<%-- 
    Document   : GraphiqueParCatégorie
    Created on : 22 nov. 2017, 13:57:29
    Author     : aubin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Graphiques des ventes</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<!-- On charge l'API Google -->
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript">
		google.load("visualization", "1", {packages: ["corechart"]});

		// Après le chargement de la page, on fait l'appel AJAX
		google.setOnLoadCallback(doAjax);
		google.setOnLoadCallback(doAjax2);
                
		function drawChart(dataArray) {
			var data = google.visualization.arrayToDataTable(dataArray);
			var options = {
				title: 'Ventes par catégorie',
				is3D: true,
                                width:450,
                                height:450
			};
			var chart = new google.visualization.PieChart(document.getElementById('piechart'));
			chart.draw(data, options);
		}

		// Afficher les ventes par client
		function doAjax() {
			$.ajax({
				url: "ServletGraphiques",
				dataType: "json",
				success: // La fonction qui traite les résultats
					function (result) {
						// On reformate le résultat comme un tableau
						var chartData = [];
						// On met le descriptif des données
						chartData.push(["Catégories", "Ventes"]);
						for(var client in result.records) {
							chartData.push([client, result.records[client]]);
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
                
                
                
                //SECOND GRAPHE
                function drawChart2(dataArray) {
			var data = google.visualization.arrayToDataTable(dataArray);
			var options = {
				title: 'Ventes par localisation',
				is3D: true,
                                width:450,
                                height:450
			};
			var chart2 = new google.visualization.PieChart(document.getElementById('piechart2'));
                        var chart3 = new google.visualization.PieChart(document.getElementById('piechart3'));
			chart2.draw(data, options);
                        chart3.draw(data, options);
		}

		// Afficher les ventes par client
		function doAjax2() {
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
						drawChart2(chartData);
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
        <form action="ServletGraphiques" method="POST">   
        Date Début : <input type="date" name="dateDebut"></br>
        Date fin : <input type="date" name="datefin"></br>
        </form>
	<!-- Le graphique apparaît ici -->
	<div id="piechart" style="width: 450px; height: 450px; display:inline-block;"></div>
        <div id="piechart3" style="width: 450px; height: 450px; display:inline-block;"></div>
        <div id="piechart2" style="width: 450px; height: 450px; display:inline-block;"></div>
    </body>
</html>

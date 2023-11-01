//Fetch data from /getDataAsCsv endpoint. data.xlsx is the name of the CSV file that is parsed via Java.
d3.csv("/getDataAsCsv?fileName=data.xlsx").then(function(data) {
  // Process the data
  data.forEach(function(d) {
    d.x = +d.x;
    d.y = +d.y;
  });

  // Create the line graph
  var svg = d3.select("div")
    .append("svg")
    .attr("width", 500)
    .attr("height", 300);

  var xScale = d3.scaleLinear()
    .domain(d3.extent(data, function(d) { return d.x; }))
    .range([0, 500]);

  var yScale = d3.scaleLinear()
    .domain(d3.extent(data, function(d) { return d.y; }))
    .range([300, 0]);

  var line = d3.line()
    .x(function(d) { return xScale(d.x); })
    .y(function(d) { return yScale(d.y); });

  svg.append("path")
    .datum(data)
    .attr("fill", "none")
    .attr("stroke", "steelblue")
    .attr("stroke-width", 2)
    .attr("d", line);
});

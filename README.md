# Approach :

#### At first Glance , the problem looked straight Forward shortest path problem but here we have dependent locations which needed to visited before certain locations. Hence I went for this approach:

<ol>
<li> First Created the Adjacency Map from the given input</li> 
<li> Next we calculated distance as suggested using haversine Formula</li>
<li> I look for finding all candidate paths where the delivery Executive can deliver orders to all customers</li>
<li> I used BFS with underlying business logic to check if the cusotmer is visited once the certain restaurant is visited first</li>
<li> Finally i calculated the shortest path from all the candidate paths</li>
</ol>



API Payload :
Sample Payload

<pre><code>
curl --location 'localhost:9090/lucidity/find-best-route' \
--header 'Content-Type: application/json' \
--data '[
{
"locationType": "DELIVERY_EXECUTIVE",
"locationLabel": "Aman",
"lat": 51.5007,
"lon": 0.1246,
"orderedFrom": "",
"connectedLocations": [
"R1",
"R2"
]
},
{
"locationType": "RESTAURANT",
"locationLabel": "R1",
"lat": 51.5015,
"lon": 0.1259,
"orderedFrom": "",
"connectedLocations": [
"Aman",
"R2",
"C1",
"C2"
]
},
{
"locationType": "RESTAURANT",
"locationLabel": "R2",
"lat": 51.5009,
"lon": 0.1248,
"orderedFrom": "",
"connectedLocations": [
"Aman",
"R1",
"C1",
"C2"
]
},
{
"locationType": "CUSTOMER",
"locationLabel": "C1",
"lat": 51.5024,
"lon": 0.1255,
"orderedFrom": "R1",
"connectedLocations": [
"R2",
"R1",
"C2"
]
},
{
"locationType": "CUSTOMER",
"locationLabel": "C2",
"lat": 51.5016,
"lon": 0.1250,
"orderedFrom": "R2",
"connectedLocations": [
"R1",
"R2",
"C1"
]
}
]'
</code></pre>



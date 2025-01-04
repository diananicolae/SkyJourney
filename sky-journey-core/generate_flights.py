from datetime import date, timedelta
import random
import json

# Define the data structures for airlines and airports
airlines = [
    "EMIRATES", "QANTAS", "LUFTHANSA", "DELTA", "UNITED",
    "AIR_CANADA", "BRITISH_AIRWAYS", "AIR_FRANCE", "AMERICAN_AIRLINES", "SINGAPORE_AIRLINES"
]

airports = [
    "LAX", "JFK", "ORD", "ATL", "LHR", "CDG", "DXB", "HND", "SYD", "FRA"
]

# Function to generate a random date
def random_date():
    start_date = date.today()
    end_date = start_date + timedelta(days=120)
    random_days = random.randint(0, (end_date - start_date).days)
    return (start_date + timedelta(days=random_days)).isoformat()

# Function to generate a random duration in the format "HH:MM"
def random_duration():
    hours = random.randint(1, 6)
    minutes = random.choice([0, 15, 30, 45])
    return f"{hours:02d}h {minutes:02d}m"

# Generate random flights
flights = []
for _ in range(1000):
    # Ensure origin and destination airports are not the same
    origin, destination = random.sample(airports, 2)
    flight = {
        "flightId": f"FL{random.randint(1000, 9999)}",
        "airline": random.choice(airlines),
        "origin": origin,
        "destination": destination,
        "date": random_date(),
        "duration": random_duration(),
        "price": random.randint(100, 700)
    }
    flights.append(flight)

# Convert to JSON
flights_json = json.dumps(flights, indent=4)

# Output to a file
with open("flights.json", "w") as file:
    file.write(flights_json)

"flights.json"


#!/bin/bash
mongoimport --host localhost --port 27017 --db tf_vehicle_health_report --collection placeholder --type json --file /placeholder.json --jsonArray
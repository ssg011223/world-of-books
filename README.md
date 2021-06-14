# world-of-books

##About the application
- The app downloads data from a predetermined API and stores It in a local database.
- Invalid data is logged to 'importLog.csv' with the id, marketplace's name, and the first detected invalid field. 
- After synchronizing a report is created from the locally stored data.
- The app has no menu nor arguments, It synchronizes, validates and creates a report in a single run.

##Setup
The required files are available in src/main/resources
- Run wob.sql file on your local database
- Fill out the fields in app.properties with the credentials of the database you ran wob.sql on
# Create the keyspace and the table on our Cassandra database
# 
CREATE KEYSPACE IF NOT EXISTS example WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE IF NOT EXISTS example.greetings (

    user text,

    id timeuuid,

    greet text,

    creation_date timestamp,

    PRIMARY KEY (user, id)

) WITH CLUSTERING ORDER BY (id DESC);

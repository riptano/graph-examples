# Migrate Legacy Graph to Native Graph


## Generate Native Graph Schema
Graph Schema Migration tool creates legacy graph frame. Based on legacy graph frame schema, the tool generates native graph schema.

There are two types of schema, cql and gremlin formats. Cql format schema is the CQL script to create graph keyspace and
tables. Gremlin format is native graph schema.
Run the following command to generate native graph schema
```
dse graph-migrate-schema -cql|-gremlin <legacy_graph> <native_graph>
```

All properties are single. Multi properties are migrated as a single property. Meta properties, Index and MVs are are
dropped. If custom handling of meta properties and multi properties, renaming properties and dropping properties are
needed, user should modify the generated native graph schema. The native graph schema can be created through cqlsh or
gremlin.

Then pass generated schema to cqlsh or gremlin-console depends on the type

Example:
```
dse graph-migrate-schema -gremlin reviewerRating nreviewerRating > native_schema.groovy
dse gremlin-console -e native_schema.groovy
```

## Migrate Data

Provided script is just an example of the data migration process.
It should work as is, if new native schema was generated with previous step and was applied without 
modifications. 

build:
```
sbt package
```

and then submit to Spark:
```
dse spark-submit target/scala-2.11/graph-migration_2.11-0.1.jar <legacy_graph> <native_graph>
```


Example:
```
dse spark-submit target/scala-2.11/graph-migration_2.11-0.1.jar reviewerRating nreviewerRating
```
### Development notes

The migration tool use spark-cassandra-connector to directly write data to DSE-DB. It enumerates native vertex labels, 
select proper rows from legacy DGF vertices and write them to  appropriate tables. See `migrateVertices()` method
Then the tool enumerate native edge labels, extract (inLabel, edgeLabel, outLabel) triplet from it, select proper rows
from legacy DGF edges, convert edge ids into native format and write data to corresponded table. See `migrateEdges()` method.
`handleMultiAndMetaProperies()` method is expected to be overridden to properly migrate multi and meta properties
    by default it just drops all  metadata and select the first of multi properties.                                  
`getLegacyPropertyName()` should be overridden if property names was manually changed in the schema generate by the 
   `graph-migrate-schema`
   
 
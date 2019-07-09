// This tool migrates classic graph schema to basic core graph schema.
// All properties are single. Multi properties are migrated as a single property.
// Meta properties, Index and MVs are are dropped. If custom handling of meta properties 
// and multi properties, renaming properties and dropping properties are needed, 
// user should modify the generated core graph schema. The core graph schema can be created through cqlsh or gremlin.
import com.datastax.bdp.graphv2.engine.Engine

system.graph('statestreet').withReplication("{ 'class' : 'org.apache.cassandra.locator.NetworkTopologyStrategy', 'SearchGraphAnalytics': '1' }").andDurableWrites(true).create()

:remote config alias g statestreet.g

schema.vertexLabel('childnums').ifNotExists().partitionBy('childnums_id', Long).property('childnums_name', Varchar).property('numsvtype', Varchar).create()
schema.vertexLabel('semiparent').ifNotExists().partitionBy('semiparent_id', Long).property('semiparent_name', Varchar).property('vtype', Varchar).property('codeA', Varchar).property('codeB', Varchar).property('strategy', Varchar).create()
schema.vertexLabel('numsmonth').ifNotExists().partitionBy('numsmonth_id', Long).property('vdate', Varchar).create()
schema.vertexLabel('valyear').ifNotExists().partitionBy('valyear_id', Long).property('vdate', Varchar).create()
schema.vertexLabel('parent').ifNotExists().partitionBy('parent_id', Long).property('parent_name', Varchar).property('domicile', Varchar).property('vtype', Varchar).property('codeA', Varchar).create()
schema.vertexLabel('numsyear').ifNotExists().partitionBy('numsyear_id', Long).property('vdate', Varchar).create()
schema.vertexLabel('nums').ifNotExists().partitionBy('nums_id', Long).property('nums_name', Varchar).property('number', Long).property('vdate', Varchar).create()
schema.vertexLabel('subparent').ifNotExists().partitionBy('subparent_id', Long).property('subparent_name', Varchar).property('codeA', Varchar).property('codeB', Varchar).property('vtype', Varchar).property('strategy', Varchar).create()
schema.vertexLabel('childitem').ifNotExists().partitionBy('childitem_id', Long).property('childitem_name', Varchar).property('trade', Varchar).property('vtype', Varchar).property('region', Varchar).create()
schema.vertexLabel('superparent').ifNotExists().partitionBy('superparent_id', Long).property('superparent_name', Varchar).property('codeA', Varchar).property('vtype', Varchar).property('codeB', Varchar).property('gencode', Varchar).create()
schema.vertexLabel('vals').ifNotExists().partitionBy('vals_id', Long).property('vals_name', Varchar).property('vvalue', Double).property('vdate', Varchar).create()
schema.vertexLabel('topchild').ifNotExists().partitionBy('topchild_id', Long).property('topchild_name', Varchar).property('family', Varchar).property('childvtype', Varchar).property('intcode', Long).property('class', Varchar).property('region', Varchar).property('codeA', Varchar).create()
schema.vertexLabel('valmonth').ifNotExists().partitionBy('valmonth_id', Long).property('vdate', Varchar).create()
schema.edgeLabel('childnums_numsyear').ifNotExists().from('childnums').to('numsyear').property('connect_year', Varchar).create()
schema.edgeLabel('childnums_childitem').ifNotExists().from('childnums').to('childitem').property('connect_date', Varchar).create()
schema.edgeLabel('semiparent_subparent').ifNotExists().from('semiparent').to('subparent').property('connect_date', Varchar).create()
schema.edgeLabel('numsmonth_nums').ifNotExists().from('numsmonth').to('nums').property('connect_date', Varchar).create()
schema.edgeLabel('valyear_valmonth').ifNotExists().from('valyear').to('valmonth').property('connect_month', Varchar).create()
schema.edgeLabel('parent_semiparent').ifNotExists().from('parent').to('semiparent').property('connect_date', Varchar).create()
schema.edgeLabel('numsyear_numsmonth').ifNotExists().from('numsyear').to('numsmonth').property('connect_month', Varchar).create()
schema.edgeLabel('subparent_topchild').ifNotExists().from('subparent').to('topchild').property('connect_date', Varchar).create()
schema.edgeLabel('childitem_valyear').ifNotExists().from('childitem').to('valyear').property('connect_year', Varchar).create()
schema.edgeLabel('superparent_parent').ifNotExists().from('superparent').to('parent').property('connect_date', Varchar).property('referenced_parent_id', Long).property('referenced_superparent_id', Long).create()
schema.edgeLabel('topchild_childnums').ifNotExists().from('topchild').to('childnums').property('connect_date', Varchar).create()
schema.edgeLabel('valmonth_vals').ifNotExists().from('valmonth').to('vals').property('connect_date', Varchar).create()
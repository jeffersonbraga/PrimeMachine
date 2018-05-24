package br.com.primum.modelo.viasoft.SYS_EXPORT_FULL_07;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Entity
@Table(name="SYS_EXPORT_FULL_07")
public class Sys_export_full_07  implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	@Column
	private String CONTROL_QUEUE;

	@Column
	private String STATUS_QUEUE;

	@Column
	private String REMOTE_LINK;

	@Column
	private Integer VERSION;

	@Column
	private String JOB_VERSION;

	@Column
	private String DB_VERSION;

	@Column
	private String TIMEZONE;

	@Column
	private String STATE;

	@Column
	private Integer PHASE;

	@Column
	private Date START_TIME;

	@Column
	private Integer BLOCK_SIZE;

	@Column
	private Integer METADATA_BUFFER_SIZE;

	@Column
	private Integer DATA_BUFFER_SIZE;

	@Column
	private Integer DEGREE;

	@Column
	private String PLATFORM;

	@Column
	private Integer ABORT_STEP;

	@Column
	private String INSTANCE;

	@Column
	private Integer CLUSTER_OK;

	@Column
	private String SERVICE_NAME;

	@Column
	private String OBJECT_INT_OID;

	@Id
	@Column
	private Integer PROCESS_ORDER;

	@Column
	private Integer DUPLICATE;

	@Column
	private Integer DUMP_FILEID;

	@Column
	private Integer DUMP_POSITION;

	@Column
	private Integer DUMP_LENGTH;

	@Column
	private Integer DUMP_ORIG_LENGTH;

	@Column
	private Integer DUMP_ALLOCATION;

	@Column
	private Integer COMPLETED_ROWS;

	@Column
	private Integer ERROR_COUNT;

	@Column
	private Integer ELAPSED_TIME;

	@Column
	private String OBJECT_TYPE_PATH;

	@Column
	private Integer OBJECT_PATH_SEQNO;

	@Column
	private String OBJECT_TYPE;

	@Column
	private String OBJECT_NAME;

	@Column
	private String OBJECT_LONG_NAME;

	@Column
	private String OBJECT_SCHEMA;

	@Column
	private String ORIGINAL_OBJECT_SCHEMA;

	@Column
	private String ORIGINAL_OBJECT_NAME;

	@Column
	private String PARTITION_NAME;

	@Column
	private String SUBPARTITION_NAME;

	@Column
	private Integer DATAOBJ_NUM;

	@Column
	private Integer FLAGS;

	@Column
	private Integer PROPERTY;

	@Column
	private Integer TRIGFLAG;

	@Column
	private Integer CREATION_LEVEL;

	@Column
	private Date COMPLETION_TIME;

	@Column
	private String OBJECT_TABLESPACE;

	@Column
	private Integer SIZE_ESTIMATE;

	@Column
	private Integer OBJECT_ROW;

	@Column
	private Integer BASE_PROCESS_ORDER;

	@Column
	private String BASE_OBJECT_TYPE;

	@Column
	private String BASE_OBJECT_NAME;

	@Column
	private String BASE_OBJECT_SCHEMA;

	@Column
	private Integer ANCESTOR_PROCESS_ORDER;

	@Column
	private Integer DOMAIN_PROCESS_ORDER;

	@Column
	private Integer PARALLELIZATION;

	@Column
	private Integer UNLOAD_METHOD;

	@Column
	private Integer LOAD_METHOD;

	@Column
	private Integer GRANULES;

	@Column
	private Integer SCN;

	@Column
	private String GRANTOR;

	@Column
	private Integer PARENT_PROCESS_ORDER;

	@Column
	private String NAME;

	@Column
	private String VALUE_T;

	@Column
	private Integer VALUE_N;

	@Column
	private Integer IS_DEFAULT;

	@Column
	private Integer FILE_TYPE;

	@Column
	private String USER_DIRECTORY;

	@Column
	private String USER_FILE_NAME;

	@Column
	private String FILE_NAME;

	@Column
	private Integer EXTEND_SIZE;

	@Column
	private Integer FILE_MAX_SIZE;

	@Column
	private String PROCESS_NAME;

	@Column
	private Date LAST_UPDATE;

	@Column
	private String WORK_ITEM;

	@Column
	private Integer OBJECT_NUMBER;

	@Column
	private Integer COMPLETED_BYTES;

	@Column
	private Integer TOTAL_BYTES;

	@Column
	private Integer METADATA_IO;

	@Column
	private Integer DATA_IO;

	@Column
	private Integer CUMULATIVE_TIME;

	@Column
	private Integer PACKET_NUMBER;

	@Column
	private Integer INSTANCE_ID;

	@Column
	private String OLD_VALUE;

	@Column
	private Integer SEED;

	@Column
	private Integer LAST_FILE;

	@Column
	private String USER_NAME;

	@Column
	private String OPERATION;

	@Column
	private String JOB_MODE;

	@Column
	private Integer QUEUE_TABNUM;


	public String getControl_queue(){
		return CONTROL_QUEUE;
	}


	public void setControl_queue(String CONTROL_QUEUE){
		this.CONTROL_QUEUE = CONTROL_QUEUE;
	}


	public String getStatus_queue(){
		return STATUS_QUEUE;
	}


	public void setStatus_queue(String STATUS_QUEUE){
		this.STATUS_QUEUE = STATUS_QUEUE;
	}


	public String getRemote_link(){
		return REMOTE_LINK;
	}


	public void setRemote_link(String REMOTE_LINK){
		this.REMOTE_LINK = REMOTE_LINK;
	}


	public Integer getVersion(){
		return VERSION;
	}


	public void setVersion(Integer VERSION){
		this.VERSION = VERSION;
	}


	public String getJob_version(){
		return JOB_VERSION;
	}


	public void setJob_version(String JOB_VERSION){
		this.JOB_VERSION = JOB_VERSION;
	}


	public String getDb_version(){
		return DB_VERSION;
	}


	public void setDb_version(String DB_VERSION){
		this.DB_VERSION = DB_VERSION;
	}


	public String getTimezone(){
		return TIMEZONE;
	}


	public void setTimezone(String TIMEZONE){
		this.TIMEZONE = TIMEZONE;
	}


	public String getState(){
		return STATE;
	}


	public void setState(String STATE){
		this.STATE = STATE;
	}


	public Integer getPhase(){
		return PHASE;
	}


	public void setPhase(Integer PHASE){
		this.PHASE = PHASE;
	}


	public Date getStart_time(){
		return START_TIME;
	}


	public void setStart_time(Date START_TIME){
		this.START_TIME = START_TIME;
	}


	public Integer getBlock_size(){
		return BLOCK_SIZE;
	}


	public void setBlock_size(Integer BLOCK_SIZE){
		this.BLOCK_SIZE = BLOCK_SIZE;
	}


	public Integer getMetadata_buffer_size(){
		return METADATA_BUFFER_SIZE;
	}


	public void setMetadata_buffer_size(Integer METADATA_BUFFER_SIZE){
		this.METADATA_BUFFER_SIZE = METADATA_BUFFER_SIZE;
	}


	public Integer getData_buffer_size(){
		return DATA_BUFFER_SIZE;
	}


	public void setData_buffer_size(Integer DATA_BUFFER_SIZE){
		this.DATA_BUFFER_SIZE = DATA_BUFFER_SIZE;
	}


	public Integer getDegree(){
		return DEGREE;
	}


	public void setDegree(Integer DEGREE){
		this.DEGREE = DEGREE;
	}


	public String getPlatform(){
		return PLATFORM;
	}


	public void setPlatform(String PLATFORM){
		this.PLATFORM = PLATFORM;
	}


	public Integer getAbort_step(){
		return ABORT_STEP;
	}


	public void setAbort_step(Integer ABORT_STEP){
		this.ABORT_STEP = ABORT_STEP;
	}


	public String getInstance(){
		return INSTANCE;
	}


	public void setInstance(String INSTANCE){
		this.INSTANCE = INSTANCE;
	}


	public Integer getCluster_ok(){
		return CLUSTER_OK;
	}


	public void setCluster_ok(Integer CLUSTER_OK){
		this.CLUSTER_OK = CLUSTER_OK;
	}


	public String getService_name(){
		return SERVICE_NAME;
	}


	public void setService_name(String SERVICE_NAME){
		this.SERVICE_NAME = SERVICE_NAME;
	}


	public String getObject_int_oid(){
		return OBJECT_INT_OID;
	}


	public void setObject_int_oid(String OBJECT_INT_OID){
		this.OBJECT_INT_OID = OBJECT_INT_OID;
	}


	public Integer getProcess_order(){
		return PROCESS_ORDER;
	}


	public void setProcess_order(Integer PROCESS_ORDER){
		this.PROCESS_ORDER = PROCESS_ORDER;
	}


	public Integer getDuplicate(){
		return DUPLICATE;
	}


	public void setDuplicate(Integer DUPLICATE){
		this.DUPLICATE = DUPLICATE;
	}


	public Integer getDump_fileid(){
		return DUMP_FILEID;
	}


	public void setDump_fileid(Integer DUMP_FILEID){
		this.DUMP_FILEID = DUMP_FILEID;
	}


	public Integer getDump_position(){
		return DUMP_POSITION;
	}


	public void setDump_position(Integer DUMP_POSITION){
		this.DUMP_POSITION = DUMP_POSITION;
	}


	public Integer getDump_length(){
		return DUMP_LENGTH;
	}


	public void setDump_length(Integer DUMP_LENGTH){
		this.DUMP_LENGTH = DUMP_LENGTH;
	}


	public Integer getDump_orig_length(){
		return DUMP_ORIG_LENGTH;
	}


	public void setDump_orig_length(Integer DUMP_ORIG_LENGTH){
		this.DUMP_ORIG_LENGTH = DUMP_ORIG_LENGTH;
	}


	public Integer getDump_allocation(){
		return DUMP_ALLOCATION;
	}


	public void setDump_allocation(Integer DUMP_ALLOCATION){
		this.DUMP_ALLOCATION = DUMP_ALLOCATION;
	}


	public Integer getCompleted_rows(){
		return COMPLETED_ROWS;
	}


	public void setCompleted_rows(Integer COMPLETED_ROWS){
		this.COMPLETED_ROWS = COMPLETED_ROWS;
	}


	public Integer getError_count(){
		return ERROR_COUNT;
	}


	public void setError_count(Integer ERROR_COUNT){
		this.ERROR_COUNT = ERROR_COUNT;
	}


	public Integer getElapsed_time(){
		return ELAPSED_TIME;
	}


	public void setElapsed_time(Integer ELAPSED_TIME){
		this.ELAPSED_TIME = ELAPSED_TIME;
	}


	public String getObject_type_path(){
		return OBJECT_TYPE_PATH;
	}


	public void setObject_type_path(String OBJECT_TYPE_PATH){
		this.OBJECT_TYPE_PATH = OBJECT_TYPE_PATH;
	}


	public Integer getObject_path_seqno(){
		return OBJECT_PATH_SEQNO;
	}


	public void setObject_path_seqno(Integer OBJECT_PATH_SEQNO){
		this.OBJECT_PATH_SEQNO = OBJECT_PATH_SEQNO;
	}


	public String getObject_type(){
		return OBJECT_TYPE;
	}


	public void setObject_type(String OBJECT_TYPE){
		this.OBJECT_TYPE = OBJECT_TYPE;
	}


	public String getObject_name(){
		return OBJECT_NAME;
	}


	public void setObject_name(String OBJECT_NAME){
		this.OBJECT_NAME = OBJECT_NAME;
	}


	public String getObject_long_name(){
		return OBJECT_LONG_NAME;
	}


	public void setObject_long_name(String OBJECT_LONG_NAME){
		this.OBJECT_LONG_NAME = OBJECT_LONG_NAME;
	}


	public String getObject_schema(){
		return OBJECT_SCHEMA;
	}


	public void setObject_schema(String OBJECT_SCHEMA){
		this.OBJECT_SCHEMA = OBJECT_SCHEMA;
	}


	public String getOriginal_object_schema(){
		return ORIGINAL_OBJECT_SCHEMA;
	}


	public void setOriginal_object_schema(String ORIGINAL_OBJECT_SCHEMA){
		this.ORIGINAL_OBJECT_SCHEMA = ORIGINAL_OBJECT_SCHEMA;
	}


	public String getOriginal_object_name(){
		return ORIGINAL_OBJECT_NAME;
	}


	public void setOriginal_object_name(String ORIGINAL_OBJECT_NAME){
		this.ORIGINAL_OBJECT_NAME = ORIGINAL_OBJECT_NAME;
	}


	public String getPartition_name(){
		return PARTITION_NAME;
	}


	public void setPartition_name(String PARTITION_NAME){
		this.PARTITION_NAME = PARTITION_NAME;
	}


	public String getSubpartition_name(){
		return SUBPARTITION_NAME;
	}


	public void setSubpartition_name(String SUBPARTITION_NAME){
		this.SUBPARTITION_NAME = SUBPARTITION_NAME;
	}


	public Integer getDataobj_num(){
		return DATAOBJ_NUM;
	}


	public void setDataobj_num(Integer DATAOBJ_NUM){
		this.DATAOBJ_NUM = DATAOBJ_NUM;
	}


	public Integer getFlags(){
		return FLAGS;
	}


	public void setFlags(Integer FLAGS){
		this.FLAGS = FLAGS;
	}


	public Integer getProperty(){
		return PROPERTY;
	}


	public void setProperty(Integer PROPERTY){
		this.PROPERTY = PROPERTY;
	}


	public Integer getTrigflag(){
		return TRIGFLAG;
	}


	public void setTrigflag(Integer TRIGFLAG){
		this.TRIGFLAG = TRIGFLAG;
	}


	public Integer getCreation_level(){
		return CREATION_LEVEL;
	}


	public void setCreation_level(Integer CREATION_LEVEL){
		this.CREATION_LEVEL = CREATION_LEVEL;
	}


	public Date getCompletion_time(){
		return COMPLETION_TIME;
	}


	public void setCompletion_time(Date COMPLETION_TIME){
		this.COMPLETION_TIME = COMPLETION_TIME;
	}


	public String getObject_tablespace(){
		return OBJECT_TABLESPACE;
	}


	public void setObject_tablespace(String OBJECT_TABLESPACE){
		this.OBJECT_TABLESPACE = OBJECT_TABLESPACE;
	}


	public Integer getSize_estimate(){
		return SIZE_ESTIMATE;
	}


	public void setSize_estimate(Integer SIZE_ESTIMATE){
		this.SIZE_ESTIMATE = SIZE_ESTIMATE;
	}


	public Integer getObject_row(){
		return OBJECT_ROW;
	}


	public void setObject_row(Integer OBJECT_ROW){
		this.OBJECT_ROW = OBJECT_ROW;
	}


	public Integer getBase_process_order(){
		return BASE_PROCESS_ORDER;
	}


	public void setBase_process_order(Integer BASE_PROCESS_ORDER){
		this.BASE_PROCESS_ORDER = BASE_PROCESS_ORDER;
	}


	public String getBase_object_type(){
		return BASE_OBJECT_TYPE;
	}


	public void setBase_object_type(String BASE_OBJECT_TYPE){
		this.BASE_OBJECT_TYPE = BASE_OBJECT_TYPE;
	}


	public String getBase_object_name(){
		return BASE_OBJECT_NAME;
	}


	public void setBase_object_name(String BASE_OBJECT_NAME){
		this.BASE_OBJECT_NAME = BASE_OBJECT_NAME;
	}


	public String getBase_object_schema(){
		return BASE_OBJECT_SCHEMA;
	}


	public void setBase_object_schema(String BASE_OBJECT_SCHEMA){
		this.BASE_OBJECT_SCHEMA = BASE_OBJECT_SCHEMA;
	}


	public Integer getAncestor_process_order(){
		return ANCESTOR_PROCESS_ORDER;
	}


	public void setAncestor_process_order(Integer ANCESTOR_PROCESS_ORDER){
		this.ANCESTOR_PROCESS_ORDER = ANCESTOR_PROCESS_ORDER;
	}


	public Integer getDomain_process_order(){
		return DOMAIN_PROCESS_ORDER;
	}


	public void setDomain_process_order(Integer DOMAIN_PROCESS_ORDER){
		this.DOMAIN_PROCESS_ORDER = DOMAIN_PROCESS_ORDER;
	}


	public Integer getParallelization(){
		return PARALLELIZATION;
	}


	public void setParallelization(Integer PARALLELIZATION){
		this.PARALLELIZATION = PARALLELIZATION;
	}


	public Integer getUnload_method(){
		return UNLOAD_METHOD;
	}


	public void setUnload_method(Integer UNLOAD_METHOD){
		this.UNLOAD_METHOD = UNLOAD_METHOD;
	}


	public Integer getLoad_method(){
		return LOAD_METHOD;
	}


	public void setLoad_method(Integer LOAD_METHOD){
		this.LOAD_METHOD = LOAD_METHOD;
	}


	public Integer getGranules(){
		return GRANULES;
	}


	public void setGranules(Integer GRANULES){
		this.GRANULES = GRANULES;
	}


	public Integer getScn(){
		return SCN;
	}


	public void setScn(Integer SCN){
		this.SCN = SCN;
	}


	public String getGrantor(){
		return GRANTOR;
	}


	public void setGrantor(String GRANTOR){
		this.GRANTOR = GRANTOR;
	}


	public Integer getParent_process_order(){
		return PARENT_PROCESS_ORDER;
	}


	public void setParent_process_order(Integer PARENT_PROCESS_ORDER){
		this.PARENT_PROCESS_ORDER = PARENT_PROCESS_ORDER;
	}


	public String getName(){
		return NAME;
	}


	public void setName(String NAME){
		this.NAME = NAME;
	}


	public String getValue_t(){
		return VALUE_T;
	}


	public void setValue_t(String VALUE_T){
		this.VALUE_T = VALUE_T;
	}


	public Integer getValue_n(){
		return VALUE_N;
	}


	public void setValue_n(Integer VALUE_N){
		this.VALUE_N = VALUE_N;
	}


	public Integer getIs_default(){
		return IS_DEFAULT;
	}


	public void setIs_default(Integer IS_DEFAULT){
		this.IS_DEFAULT = IS_DEFAULT;
	}


	public Integer getFile_type(){
		return FILE_TYPE;
	}


	public void setFile_type(Integer FILE_TYPE){
		this.FILE_TYPE = FILE_TYPE;
	}


	public String getUser_directory(){
		return USER_DIRECTORY;
	}


	public void setUser_directory(String USER_DIRECTORY){
		this.USER_DIRECTORY = USER_DIRECTORY;
	}


	public String getUser_file_name(){
		return USER_FILE_NAME;
	}


	public void setUser_file_name(String USER_FILE_NAME){
		this.USER_FILE_NAME = USER_FILE_NAME;
	}


	public String getFile_name(){
		return FILE_NAME;
	}


	public void setFile_name(String FILE_NAME){
		this.FILE_NAME = FILE_NAME;
	}


	public Integer getExtend_size(){
		return EXTEND_SIZE;
	}


	public void setExtend_size(Integer EXTEND_SIZE){
		this.EXTEND_SIZE = EXTEND_SIZE;
	}


	public Integer getFile_max_size(){
		return FILE_MAX_SIZE;
	}


	public void setFile_max_size(Integer FILE_MAX_SIZE){
		this.FILE_MAX_SIZE = FILE_MAX_SIZE;
	}


	public String getProcess_name(){
		return PROCESS_NAME;
	}


	public void setProcess_name(String PROCESS_NAME){
		this.PROCESS_NAME = PROCESS_NAME;
	}


	public Date getLast_update(){
		return LAST_UPDATE;
	}


	public void setLast_update(Date LAST_UPDATE){
		this.LAST_UPDATE = LAST_UPDATE;
	}


	public String getWork_item(){
		return WORK_ITEM;
	}


	public void setWork_item(String WORK_ITEM){
		this.WORK_ITEM = WORK_ITEM;
	}


	public Integer getObject_number(){
		return OBJECT_NUMBER;
	}


	public void setObject_number(Integer OBJECT_NUMBER){
		this.OBJECT_NUMBER = OBJECT_NUMBER;
	}


	public Integer getCompleted_bytes(){
		return COMPLETED_BYTES;
	}


	public void setCompleted_bytes(Integer COMPLETED_BYTES){
		this.COMPLETED_BYTES = COMPLETED_BYTES;
	}


	public Integer getTotal_bytes(){
		return TOTAL_BYTES;
	}


	public void setTotal_bytes(Integer TOTAL_BYTES){
		this.TOTAL_BYTES = TOTAL_BYTES;
	}


	public Integer getMetadata_io(){
		return METADATA_IO;
	}


	public void setMetadata_io(Integer METADATA_IO){
		this.METADATA_IO = METADATA_IO;
	}


	public Integer getData_io(){
		return DATA_IO;
	}


	public void setData_io(Integer DATA_IO){
		this.DATA_IO = DATA_IO;
	}


	public Integer getCumulative_time(){
		return CUMULATIVE_TIME;
	}


	public void setCumulative_time(Integer CUMULATIVE_TIME){
		this.CUMULATIVE_TIME = CUMULATIVE_TIME;
	}


	public Integer getPacket_number(){
		return PACKET_NUMBER;
	}


	public void setPacket_number(Integer PACKET_NUMBER){
		this.PACKET_NUMBER = PACKET_NUMBER;
	}


	public Integer getInstance_id(){
		return INSTANCE_ID;
	}


	public void setInstance_id(Integer INSTANCE_ID){
		this.INSTANCE_ID = INSTANCE_ID;
	}


	public String getOld_value(){
		return OLD_VALUE;
	}


	public void setOld_value(String OLD_VALUE){
		this.OLD_VALUE = OLD_VALUE;
	}


	public Integer getSeed(){
		return SEED;
	}


	public void setSeed(Integer SEED){
		this.SEED = SEED;
	}


	public Integer getLast_file(){
		return LAST_FILE;
	}


	public void setLast_file(Integer LAST_FILE){
		this.LAST_FILE = LAST_FILE;
	}


	public String getUser_name(){
		return USER_NAME;
	}


	public void setUser_name(String USER_NAME){
		this.USER_NAME = USER_NAME;
	}


	public String getOperation(){
		return OPERATION;
	}


	public void setOperation(String OPERATION){
		this.OPERATION = OPERATION;
	}


	public String getJob_mode(){
		return JOB_MODE;
	}


	public void setJob_mode(String JOB_MODE){
		this.JOB_MODE = JOB_MODE;
	}


	public Integer getQueue_tabnum(){
		return QUEUE_TABNUM;
	}


	public void setQueue_tabnum(Integer QUEUE_TABNUM){
		this.QUEUE_TABNUM = QUEUE_TABNUM;
	}

}
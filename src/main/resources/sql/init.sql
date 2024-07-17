drop table if exists WorkedOn;
drop table if exists SequenceLine;
drop table if exists WorkOrder;
drop table if exists SequenceHeader;
drop table if exists SaleOrderLine;
drop table if exists SaleOrderHeader;
drop table if exists Staff;
drop table if exists WorkStation;
drop table if exists Part;
drop table if exists Customer;

create table Customer
(
    customerId VarChar(8) PRIMARY Key,
    name VarChar(100) not null,
    address VarChar(500) not null
);


create table Part
(
	partId VarChar(6) PRIMARY KEY,
	inventory Int not null,
	name VarChar(100) not null
);

create table WorkStation
(
	name VarChar(50) PRIMARY KEY
);

create table Staff
(
	staffID VarChar(6) PRIMARY KEY,
	name VarChar(100) not null
);

create table SaleOrderHeader
(
	soHeaderId VarChar(6) PRIMARY key ,
	customerId VarChar(8) not null references Customer (customerId)
);

create table SaleOrderLine
(
	soLineId VarChar(6) PRIMARY KEY,
	soHeaderId VarChar(6) not null references SaleOrderHeader (soHeaderID),
	dateDue Date not null,
	partID VarChar(6) not null references Part(partId)
);

create table SequenceHeader
(
	seqHeaderId VarChar(6) PRIMARY KEY,
	partId VarChar(6) not null references Part(partId)
);

create table WorkOrder
(
	workOrderId VarChar(8) PRIMARY KEY,
	seqHeaderId VarChar(6) not null REFERENCES SequenceHeader (seqHeaderId),
	soLineId VarChar(6) not null REFERENCES SaleOrderLine (soLineId),
	quanity Int not null
);

create table SequenceLine
(
	seqLineId VarChar(10) PRIMARY KEY,
	seqHeaderId VarChar(6) not null references SequenceHeader (seqHeaderId),
	workStation VarChar(6) not null references WorkStation(name)
);

create table WorkedOn
(
	staffId char(8) references Staff (staffId),
	seqLineId char(10) references SequenceLine (seqLineId),
	workOrderId char(8) references WorkOrder(workOrderId),

	PRIMARY key (staffId,seqLineId,workOrderId)
);
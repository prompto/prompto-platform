import sys
from bson.binary import Binary, STANDARD, JAVA_LEGACY
from bson.codec_options import CodecOptions
from pymongo import MongoClient, database


def migrateOne(db:database.Database):
    old = db.get_collection("instances_old")
    new = db.get_collection("instances")
    # if already migrated return
    if old.count() == new.count():
        return
    # if previous migration failed restore state
    if old.count() > 0 and new.count() == 0:
        new.drop()
        old = db.get_collection("instances_old")
        old.rename("instances")
    # migrate
    new.rename("instances_old")
    old = db.get_collection("instances_old", CodecOptions(uuid_representation=JAVA_LEGACY))
    new = db.get_collection("instances", CodecOptions(uuid_representation=STANDARD))
    # don't overwrite data
    if new.count() > 0:
        return
    for record in old.find():
        new.insert(record)


def migrateAll(client:MongoClient):
    for name in client.database_names():
        if name=='admin' or name=='local':
            continue
        db = client[name]
        migrateOne(db)


def clientFromArgs(args:[])->MongoClient:
    host = args[1]
    port = int(args[2])
    if len(args)==3:
        return MongoClient(host, port)
    user = args[3]
    pwd = args[4]
    return MongoClient(host, port, username=user, password=pwd)

if __name__ == "__main__":
    client = clientFromArgs(sys.argv)
    migrateAll(client)
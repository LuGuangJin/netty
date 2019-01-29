namespace java thrift.generated

typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

struct Person {
    1: String userName,
    2: int age,
    3: boolean married
}

exception DataException {
    1: String msg,
    2: String stackCause,
    3: String date
}

service PersonService {
    Person getPersonByUserName(1: required String userName) throws (1:DataException dataException),
    int savePerson(1: required Person p) throws (1:DataException dataException)
}
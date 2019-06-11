import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// export const helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });


export const onFriendLineUserDeleted = functions.firestore
.document('users/{auth_id}').onDelete(
    (snapshot, context) => {
        /*
        "users":{
            "auth_id":"authentication user id",
            "firstname":"string",
            "lastname":"string",
            "email":"string",
            "image":"url to image",
            "birthday":"date",
            "phone":"integer",
            "gender":"string",
            "city":"string",
            "country":"string",
            "educations": {
                "institution":"string",
                "type":"string" 
            }
        */
        const c = admin.firestore().collection('frienships/{id}');
        const id = context.params.auth_id;
        console.log('User deleted: ' + id);
        console.log(c);
})
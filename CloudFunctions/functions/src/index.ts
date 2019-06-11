const functions = require('firebase-functions');

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

export const deleteUserRemnants = functions.firestore.document('users/{user_id}').onDelete((snap: any, context: any) => {
    const deleted = snap.data();
    console.log("User deleted: " + deleted.path);
});
import functions = require('firebase-functions');

export const deleteUserRemnants = functions.auth.user().onDelete((user: functions.auth.UserRecord) => {
    console.log("User deleted: " + user.email);
    console.log("User deleted: " + user.uid);
});
## REST Controller 1: UserController

This REST controller is responsible for managing user-related operations.

### Endpoints:

1. `GET /users/logout`
    - Description: Log out the current user and set the online status to false.
    - Response: "Logged out" (String)

2. `POST /users/create`
    - Description: Create a new user.
    - Request Body: JSON representation of the user object to create.
    - Response: JSON representation of the created user object.

3. `PATCH /users/changeUsername/{id}/{username}`
    - Description: Change the username of an existing user.
    - Path Parameters:
        - `{id}`: The ID of the user to update.
        - `{username}`: The new username to set.
    - Response: JSON representation of the updated user object.

4. `DELETE /users/delete/{id}`
    - Description: Delete a user by ID. (Requires ROLE_ADMIN authorization)
    - Path Parameter:
        - `{id}`: The ID of the user to delete.
    - Response: JSON representation of the deleted user object.

5. `GET /users/findByUsername/{username}`
    - Description: Find a user by their username.
    - Path Parameter:
        - `{username}`: The username of the user to find.
    - Response: JSON representation of the found user object.

6. `GET /users/findById/{id}`
    - Description: Find a user by their ID. (Requires ROLE_ADMIN authorization)
    - Path Parameter:
        - `{id}`: The ID of the user to find.
    - Response: JSON representation of the found user object.

7. `GET /users/listAll`
    - Description: Get a list of all users. (Requires ROLE_ADMIN authorization)
    - Response: JSON array containing all user objects.

8. `GET /users/me`
    - Description: Get the currently authenticated user.
    - Response: JSON representation of the current user object.

9. `GET /users/allOnline/{online}`
    - Description: Get a list of all online users based on the online status.
    - Path Parameter:
        - `{online}`: Boolean value to filter online users (true) or offline users (false).
    - Response: JSON array containing online user objects.

10. `GET /users/join/{chatRoomName}`
    - Description: Join the current user to a chat room.
    - Path Parameter:
        - `{chatRoomName}`: The name of the chat room to join.
    - Response: JSON representation of the user object after joining the chat room.

11. `GET /users/addRole/{username}/{roleName}`
    - Description: Add a role to a user by their username. (Requires ROLE_ADMIN authorization)
    - Path Parameters:
        - `{username}`: The username of the user to add the role.
        - `{roleName}`: The role name to add to the user.
    - Response: JSON representation of the user object after adding the role.

12. `GET /users/token/refresh`
    - Description: Refresh the access token using the refresh token.
    - Request Header: The request should include the "Authorization" header with the refresh token (Bearer).
    - Response: JSON object containing the new access_token and refresh_token.

Please note that some endpoints require specific authorization roles for access. The access token for authenticated users should be included in the request headers for endpoints that require authorization.

The UserController is responsible for user management and provides endpoints for user creation, updating, and deletion. Additionally, it allows users to log out, view their own profile information, and refresh their access token using the provided refresh token. Some endpoints require ROLE_ADMIN authorization for administrative operations.

## REST Controller 2: RoleController

This REST controller is responsible for managing role-related operations.

### Endpoints:

1. `GET /role/find/{role}`
    - Description: Find a role by its name. (Requires ROLE_ADMIN authorization)
    - Path Parameter:
        - `{role}`: The name of the role to find.
    - Response: JSON representation of the found role object.

2. `GET /role/create`
    - Description: Create a new role. (Requires ROLE_ADMIN authorization)
    - Request Body: JSON representation of the role object to create.
    - Response: JSON representation of the created role object.

3. `PUT /role/change/{id}/{text}`
    - Description: Change the text of an existing role. (Requires ROLE_ADMIN authorization)
    - Path Parameters:
        - `{id}`: The ID of the role to update.
        - `{text}`: The new text to set for the role.
    - Response: JSON representation of the updated role object.

4. `DELETE /role/delete/{id}`
    - Description: Delete a role by ID. (Requires ROLE_ADMIN authorization)
    - Path Parameter:
        - `{id}`: The ID of the role to delete.
    - Response: A message indicating the success of the deletion.

The RoleController provides endpoints for role management, allowing administrators to find, create, update, and delete roles. Access to these endpoints requires ROLE_ADMIN authorization, which means only users with the "ROLE_ADMIN" role can perform these role-related operations.

## REST Controller 3: MessageController

This REST controller is responsible for managing message-related operations.

### Endpoints:

1. `GET /message/sendNew/{recipient_username}/{content}/{name}`
    - Description: Send a new message to a recipient with a new chat room.
    - Path Parameters:
        - `{recipient_username}`: The username of the message recipient.
        - `{content}`: The content of the message.
        - `{name}`: The name of the new chat room.
    - Response: JSON representation of the sent message object.

2. `GET /message/sendToChat/{chat_name}/{content}`
    - Description: Send a message to an existing chat room.
    - Path Parameters:
        - `{chat_name}`: The name of the existing chat room.
        - `{content}`: The content of the message.
    - Response: A message indicating the success of sending the message.

3. `GET /message/getMyMessages`
    - Description: Get all messages sent to the current user.
    - Response: List of JSON representations of the user's messages.

4. `DELETE /message/delete/{id}`
    - Description: Delete a message by its ID. (Requires ROLE_ADMIN authorization)
    - Path Parameter:
        - `{id}`: The ID of the message to delete.
    - Response: A message indicating the success of the deletion.

5. `GET /message/sendDelayedMessageToChat/{chatroom}/{min}/{content}`
    - Description: Send a delayed message to an existing chat room.
    - Path Parameters:
        - `{chatroom}`: The name of the existing chat room.
        - `{min}`: The number of minutes to delay the message.
        - `{content}`: The content of the message.
    - Response: A scheduled future representing the delayed message task.

The MessageController provides endpoints for sending new messages with new chat rooms, sending messages to existing chat rooms, retrieving a user's messages, and sending delayed messages to existing chat rooms. Additionally, it allows administrators to delete messages by their ID. Some operations may require ROLE_ADMIN authorization, which means only users with the "ROLE_ADMIN" role can perform these message-related operations.

## REST Controller 4: ChatRoomController

This REST controller is responsible for managing chat room-related operations.

### Endpoints:

1. `DELETE /chatroom/delete/{id}`
    - Description: Delete a chat room by its ID. (Requires ROLE_ADMIN authorization)
    - Path Parameter:
        - `{id}`: The ID of the chat room to delete.
    - Response: A message indicating the success of the deletion.

2. `GET /chatroom/changeName/{name}`
    - Description: Change the name of the chat room.
    - Path Parameter:
        - `{name}`: The new name of the chat room.
    - Response: JSON representation of the updated chat room object.

The ChatRoomController provides endpoints for deleting chat rooms by their ID and changing the name of the chat room. Some operations may require ROLE_ADMIN authorization, which means only users with the "ROLE_ADMIN" role can perform these chat room-related operations.

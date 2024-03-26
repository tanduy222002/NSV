import { PayloadAction, createSlice } from '@reduxjs/toolkit';

type AuthUser = {
    username: string;
    email: string;
    phoneNo: string;
};

type AuthSliceState = {
    value: AuthUser | null;
};

const initState: AuthSliceState = { value: null };
// const initState: AuthSliceState = {
//     value: {
//         username: 'test user',
//         email: 'user@example.com',
//         phoneNo: '0xxx012345'
//     }
// };

export const authSlice = createSlice({
    name: 'auth',
    initialState: initState,
    reducers: {
        loggedIn: (_, action: PayloadAction<AuthSliceState>) => action.payload,
        loggedOut: () => initState
    }
});

export const { loggedIn, loggedOut } = authSlice.actions;

export default authSlice.reducer;

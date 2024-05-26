import { PayloadAction, createSlice } from '@reduxjs/toolkit';

type AuthUser = {
    id: number;
    username: string;
    email: string;
    phoneNo: string;
    avatar: string;
    roles?: string[];
};

type AuthSliceState = {
    value: AuthUser | null;
};

const initState: AuthSliceState = { value: null };

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

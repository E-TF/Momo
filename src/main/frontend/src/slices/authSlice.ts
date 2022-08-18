import {createSlice, PayloadAction} from "@reduxjs/toolkit";

export interface AuthState {
    accessToken: string | null,
    refreshToken: string | null,
    isLoggedIn: boolean
};

export interface Tokens {
    accessToken: string,
    refreshToken: string,
};

const initialState: AuthState = {
    accessToken: null,
    refreshToken: null,
    isLoggedIn: false
};

export const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        login: (state, action: PayloadAction<Tokens>) => {
            const accessToken = action.payload.accessToken;
            const refreshToken = action.payload.refreshToken;
            localStorage.setItem("accessToken", accessToken);
            localStorage.setItem("refreshToken", refreshToken);
            state.accessToken = accessToken;
            state.refreshToken = refreshToken;
            state.isLoggedIn = true;
        },

        logout: (state) => {
            localStorage.clear();
            state.accessToken = null;
            state.refreshToken = null;
            state.isLoggedIn = false;
        }
    }
});

export const {login, logout} = authSlice.actions;
export default authSlice.reducer;
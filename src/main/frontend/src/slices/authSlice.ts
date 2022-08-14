import {createSlice, PayloadAction} from "@reduxjs/toolkit";

export interface AuthState {
    accessToken : string,
    refreshToken : string,
    isLoggedIn : boolean
}

export interface Tokens{
    accessToken : string,
    refreshToken : string,
}

const initialState:AuthState = {
    accessToken : '',
    refreshToken: '',
    isLoggedIn : false
}

export const authSlice = createSlice({
    name : 'auth',
    initialState,
    reducers:{
        login:(state, action:PayloadAction<Tokens>)=>{
            state.accessToken = action.payload.accessToken;
            state.refreshToken = action.payload.refreshToken;
            state.isLoggedIn = true;
        },
        logout:(state) => {
            localStorage.clear();
            state.accessToken = '';
            state.refreshToken = '';
            state.isLoggedIn = false;
        }
    }
})

export const {login, logout} = authSlice.actions;
export default authSlice.reducer;
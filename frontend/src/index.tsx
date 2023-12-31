import React from 'react';
import ReactDOM from 'react-dom/client';
import Root from './views/root/Root';
import reportWebVitals from './reportWebVitals';

// styles
import './styles/root.scss';
import './styles/theme-colors.scss';
import "./assets/fontello/css/fontello.css";

// providers
import {HelmetProvider} from "react-helmet-async";
import LoginProvider from "./providers/login/login-provider.component";

// router
import {BrowserRouter as Router} from "react-router-dom";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <HelmetProvider>
      <LoginProvider>
        <Router>
          <Root />
        </Router>
      </LoginProvider>
    </HelmetProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

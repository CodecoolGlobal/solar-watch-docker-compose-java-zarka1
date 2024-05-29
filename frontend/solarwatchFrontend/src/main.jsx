import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './Pages/App.jsx'
import './index.css'
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Layout from "./Pages/Layout/Layout.jsx";
import Registration from './Pages/Registration.jsx';
import Login from './Pages/Login.jsx';
import UserPage from './Pages/Userpage.jsx'

const router = createBrowserRouter([
  {
    path: "/",
    element: <App/>,
  },
  {
    path: "/registration",
    element: <Registration />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/userpage",
    element: <Layout />,
    children: [
      { path: "/userpage", 
        element: <UserPage /> 
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>,
)

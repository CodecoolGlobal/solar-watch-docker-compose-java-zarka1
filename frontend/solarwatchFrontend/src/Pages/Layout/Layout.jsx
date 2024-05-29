import { Outlet, NavLink, Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

import "./Layout.css";

const Layout = () => {
  const navigate = useNavigate();
  async function logout(){
    console.log("cs")
    localStorage.setItem("jwt", "")
    navigate("/login")
    window.location.reload();
  }

  return (
    <div className="Layout">
      <nav>
      <Link to="/userpage">
        <button className="Logo" onClick={logout} >Logout</button>
        </Link>
      </nav>
      <Outlet />
    </div>
  );
};

export default Layout;
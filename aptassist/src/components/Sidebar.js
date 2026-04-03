import React from "react";
import { Nav, NavItem, NavLink } from "reactstrap";
import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <div
      style={{
        width: "240px",
        minHeight: "calc(100vh - 56px)",
        background: "#ffffff",
        padding: "20px",
        borderRight: "1px solid #e0e0e0",
        boxShadow: "2px 0 5px rgba(0,0,0,0.05)"
      }}
    >
      <h5
        style={{
          marginBottom: "25px",
          fontWeight: "600",
          color: "#343a40",
          letterSpacing: "0.5px"
        }}
      >
        Navigation
      </h5>

      <Nav vertical>
        <NavItem>
          <NavLink
            tag={Link}
            to="/"
            style={{
              cursor: "pointer",
              padding: "10px 15px",
              borderRadius: "6px",
              color: "#495057",
              fontWeight: "500",
              transition: "all 0.2s ease-in-out"
            }}
            activeStyle={{
              backgroundColor: "#007bff",
              color: "#fff"
            }}
          >
            Home
          </NavLink>
        </NavItem>

        <NavItem>
          <NavLink
            tag={Link}
            to="/add-user"
            style={{
              cursor: "pointer",
              padding: "10px 15px",
              borderRadius: "6px",
              color: "#495057",
              fontWeight: "500",
              transition: "all 0.2s ease-in-out"
            }}
            activeStyle={{
              backgroundColor: "#007bff",
              color: "#fff"
            }}
          >
            Add New User
          </NavLink>
        </NavItem>
      </Nav>
    </div>
  );
};

export default Sidebar;
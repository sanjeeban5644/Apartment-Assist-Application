import React from "react";
import { Navbar, NavbarBrand } from "reactstrap";

const Header = () => {
  return (
    <Navbar color="dark" dark expand="md" style={{ paddingLeft: "20px" }}>
      <NavbarBrand href="/">
        Apartment Assist
      </NavbarBrand>
    </Navbar>
  );
};

export default Header;
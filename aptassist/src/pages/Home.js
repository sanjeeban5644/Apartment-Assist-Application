import React from "react";
import { Card, CardBody, CardTitle, CardText } from "reactstrap";

const Home = () => {
  return (
    <Card>
      <CardBody>
        <CardTitle tag="h4">Welcome to Apartment Assist</CardTitle>

        <CardText style={{ marginTop: "10px" }}>
          Manage apartment residents, users, and records easily.
        </CardText>

        <CardText>
          Use the navigation panel on the left to add or manage users.
        </CardText>
      </CardBody>
    </Card>
  );
};

export default Home;
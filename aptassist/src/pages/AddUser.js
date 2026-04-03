import React, { useState } from "react";
import { getUser, addUser } from "../services/userService";

import {
  Card,
  CardBody,
  CardTitle,
  Form,
  FormGroup,
  Label,
  Input,
  Button,
  Row,
  Col
} from "reactstrap";

const AddUser = () => {

  const [userId, setUserId] = useState("");

  const [user, setUser] = useState({
    name: "",
    mobile: "",
    email: "",
    dob: "",
    aadharNumber: "",
    panNo: "",
    userName: "",
    password: ""
  });

  const handleChange = (e) => {
    setUser({
      ...user,
      [e.target.name]: e.target.value
    });
  };

  /*
  Fetch user from Spring Boot
  */
const fetchUser = async () => {
  try {

    const response = await getUser(userId);

    if (response.data) {
      setUser(response.data);
      alert("User Found and Loaded");
    }

  } catch (error) {

    alert("User Not Found");
    setUser({
        name: "",
        mobile: "",
        email: "",
        dob: "",
        aadharNumber: "",
        panNo: "",
        userName: "",
        password: ""
    });

  }
};
  /*
  Save new user
  */
const saveUser = async () => {

  try {

    const response = await addUser(user);

    alert("User Saved Successfully");

    console.log(response.data);

  } catch (error) {

    alert("Error saving user");

    console.error(error);

  }

};

  return (
    <Card>
      <CardBody>

        <CardTitle tag="h4">Add New User</CardTitle>

        <hr />

        <Row className="mb-4">

          <Col md="6">
            <Input
              placeholder="Enter User ID"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
            />
          </Col>

          <Col md="3">
            <Button color="primary" onClick={fetchUser}>
              Fetch User
            </Button>
          </Col>

        </Row>

        <Form>

          <Row>

            <Col md="6">
              <FormGroup>
                <Label>Name</Label>
                <Input name="name" value={user.name} onChange={handleChange}/>
              </FormGroup>
            </Col>

            <Col md="6">
              <FormGroup>
                <Label>Mobile</Label>
                <Input name="mobile" value={user.mobile} onChange={handleChange}/>
              </FormGroup>
            </Col>

          </Row>

          <Row>

            <Col md="6">
              <FormGroup>
                <Label>Email</Label>
                <Input name="email" value={user.email} onChange={handleChange}/>
              </FormGroup>
            </Col>

            <Col md="6">
              <FormGroup>
                <Label>DOB</Label>
                <Input type="date" name="dob" value={user.dob} onChange={handleChange}/>
              </FormGroup>
            </Col>

          </Row>

          <Row>

            <Col md="6">
              <FormGroup>
                <Label>Aadhar</Label>
                <Input name="aadharNumber" value={user.aadharNumber} onChange={handleChange}/>
              </FormGroup>
            </Col>

            <Col md="6">
              <FormGroup>
                <Label>PAN</Label>
                <Input name="panNo" value={user.panNo} onChange={handleChange}/>
              </FormGroup>
            </Col>

          </Row>

          <Row>

            <Col md="6">
              <FormGroup>
                <Label>Username</Label>
                <Input name="userName" value={user.userName} onChange={handleChange}/>
              </FormGroup>
            </Col>

            <Col md="6">
              <FormGroup>
                <Label>Password</Label>
                <Input type="password" name="password" value={user.password} onChange={handleChange}/>
              </FormGroup>
            </Col>

          </Row>

          <Button color="success" type="button" onClick={saveUser}>
            Save User
            </Button>

        </Form>

      </CardBody>
    </Card>
  );
};

export default AddUser;
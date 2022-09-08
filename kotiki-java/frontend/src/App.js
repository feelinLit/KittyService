import './App.css';
import React, {Component} from "react";
import {Modal} from "@mui/material";
import LogInForm from "./components/logInForm";
import {NavBar} from "./components/navBar";
import KittyTable from "./components/KittyTable";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      auth: {username: '', password: ''},
      isLoggedIn: false,
      isAuthFailed: false,
      kitties: [],
      isLogInFormOpen: false,
    };

    this.handleLogIn = this.handleLogIn.bind(this);
    this.handleLogOut = this.handleLogOut.bind(this);
    this.handleKittyRemove = this.handleKittyRemove.bind(this);
  }

  handleLogIn(e, username, password) {
    e.preventDefault();
    if (username === undefined) return;
    const header = btoa(`${username}:${password}`);
    fetch('/api/kitty',
      {
        headers: {
          'Authorization': `Basic ${header}`,
          'Accept': '*/*',
          'Content-Type': 'application/json'
        }
      })
      .then(res => {
        if (res.status === 401) {
          this.setState({isAuthFailed: true});
          throw Error("Authentication failed.");
        }
        return res.json();
      })
      .then(data => this.setState({
        isAuthFailed: false,
        isLogInFormOpen: false,
        auth: {username, password},
        isLoggedIn: true,
        kitties: data
      }))
      .catch(error => console.error(error));
  }

  handleLogOut(e) {
    e.preventDefault();
    this.setState({isLogInFormOpen: false, auth: {username: '', password: ''}, isLoggedIn: false, kitties: []})
  }

  handleKittyRemove(id) {
    const kitties = this.state.kitties.filter(kitty => kitty.id !== id);
    this.setState({kitties: kitties});
  }

  render() {
    const {kitties, auth} = this.state;

    const authHeader = `Basic ${btoa(`${auth.username}:${auth.password}`)}`

    const handleOpen = () => this.setState({isLogInFormOpen: true});
    const handleClose = () => this.setState({isLogInFormOpen: false});

    return (
      <div className="App">
        <NavBar onOpenLogInForm={handleOpen} onLogOut={this.handleLogOut} isLoggedIn={this.state.isLoggedIn}
                userName={this.state.auth.username}/>
        <main className="App-main">
          <KittyTable
            initialRows={kitties}
            onKittyRemove={this.handleKittyRemove}
            authHeader={authHeader}
            sx={{
              width: 750,
              height: 400,
            }}
          />
        </main>

        <Modal
          open={this.state.isLogInFormOpen}
          onClose={handleClose}
        >
          <LogInForm
            onSubmit={this.handleLogIn}
            authfailed={this.state.isAuthFailed}
            sx={{
              position: 'absolute',
              top: '50%',
              left: '50%',
              transform: 'translate(-50%, -50%)',
              boxShadow: 12,
              backgroundColor: 'background.paper',
              width: '40vw',
            }}
          />
        </Modal>
      </div>
    );
  }
}

export default App;

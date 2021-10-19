import React, {Component} from 'react';
import EmployeeService from "../services/EmployeeService";

class LoginComponent extends Component {
    constructor(props) {
        super(props);
		this.state = {
			userName : "",
			password: "",
			message: ""
		};
		
    }
	login = (event) => {
		event.preventDefault();
		
		EmployeeService.getEmployeeById(this.state.userName, this.state.password).then(res => {
			console.log(JSON.stringify(res.data), typeof res.data);
			let strData = JSON.stringify(res.data);
			if (strData === "") {
				console.log("if");
				this.state.message = "Incorrect Username/Password!!";
			} else {
				console.log("else");
				let employee = JSON.parse(JSON.stringify(res.data));
				if (employee.role === "admin") {
					this.props.history.push("/admin");
				} else if (employee.role === "employee") {
					this.props.history.push("/view-employee");
				}
			}	
			console.log(this.state.userName, this.state.password,this.state.message);
		});
		//this.props.history.push("/admin");
	}


    render() {
        return (
            <div>
                <div className={"container mt-3"}>
                    <div className={"row "}>
                        <div className={"card col-md-6 offset-md-3 offset-md-3"}>
                            <h3 className={"text-center"}>Login Information</h3>
                            <div className={"card-body"}>
                                <form>
                                    <div className={"form-group"}>
                                        <label className={"col-sm-3 remove-left"}> Username: </label> 
										<input 
											className={"col-sm-9 "} 
											placeholder={"Please Enter Username"}
											value={this.state.userName}
											onChange={event => this.setState({userName : event.target.value, message : ""})}
											required
										/>
                                    </div>
                                    <div className={"form-group"}>
                                        <label className={"col-sm-3 remove-left"}> Password: </label>
										<input 
											className={"col-sm-9"} 
											placeholder={"Please Enter Password"}
											value={this.state.password}
											onChange={event => this.setState({password : event.target.value, message : ""})}
											required
										/>                                        
                                    </div>
                                    
                                    <button 
										className={"btn btn-success"}
										onClick={this.login} >Login</button>
                                    <button className={"btn btn-danger ml-2"} >Clear</button>
									<label className={"error-message"}>{this.state.message}</label>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default LoginComponent;
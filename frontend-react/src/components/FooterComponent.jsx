import React, {Component} from 'react';

class FooterComponent extends Component {
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <div>
                <footer className={"footer"}>
                    <span className={ "text-muted"}>UEL Employee Management</span>
                </footer>
            </div>
        );
    }
}

export default FooterComponent;
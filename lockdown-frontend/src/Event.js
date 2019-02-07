import React, {Component} from 'react';
import PropTypes from 'prop-types';
export default class Event extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <span><a href={this.props.eventAction}>{this.props.eventTitle}</a></span>
    }
}

Event.propTypes = {
    eventAction: PropTypes.string.isRequired,
    eventTitle: PropTypes.string.isRequired
}
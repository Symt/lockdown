import React from 'react';
import PropTypes from 'prop-types';


export default function Event({ eventAction, eventTitle }) {
  return <span><a href={eventAction}>{eventTitle}</a></span>;
}

Event.propTypes = {
  eventAction: PropTypes.string.isRequired,
  eventTitle: PropTypes.string.isRequired,
};

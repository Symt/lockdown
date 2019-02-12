/* eslint jsx-a11y/anchor-is-valid: 0 */
/* eslint no-script-url: 0 */
import React from 'react';
import PropTypes from 'prop-types';

export default function Event({ eventAction, eventTitle }) {
  return <a href="javascript:void(0)" onClick={() => handler.callEvent(eventAction)}>{eventTitle}</a>;
}

Event.propTypes = {
  eventAction: PropTypes.string.isRequired,
  eventTitle: PropTypes.string.isRequired,
};

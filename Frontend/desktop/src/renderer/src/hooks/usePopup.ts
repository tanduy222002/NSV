import { useState } from 'react';

export function usePopup() {
    const [showPopup, setShowPopup] = useState(false);
    const show = () => setShowPopup(true);
    const hide = () => setShowPopup(false);

    return { showPopup, show, hide };
}

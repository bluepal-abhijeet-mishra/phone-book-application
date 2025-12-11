const ContactForm = ({ onSave, currentContact }) => {
    const [name, setName] = React.useState(currentContact ? currentContact.name : '');
    const [phoneNumber, setPhoneNumber] = React.useState(currentContact ? currentContact.phoneNumber : '');

    React.useEffect(() => {
        setName(currentContact ? currentContact.name : '');
        setPhoneNumber(currentContact ? currentContact.phoneNumber : '');
    }, [currentContact]);

    const handleSubmit = (e) => {
        e.preventDefault();
        onSave({ id: currentContact ? currentContact.id : null, name, phoneNumber });
    };

    return (
        <div>
            <h2>{currentContact ? 'Edit Contact' : 'Add Contact'}</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name</label>
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
                </div>
                <div>
                    <label>Phone Number</label>
                    <input type="text" value={phoneNumber} onChange={(e) => setPhoneNumber(e.target.value)} />
                </div>
                <button type="submit">Save</button>
            </form>
        </div>
    );
};

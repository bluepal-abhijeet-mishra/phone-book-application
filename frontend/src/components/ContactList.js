const ContactList = ({ contacts, onEdit, onDelete }) => {
    return (
        <div>
            <h2>Contact List</h2>
            <ul>
                {contacts.map((contact) => (
                    <li key={contact.id}>
                        <span>{contact.name} - {contact.phoneNumber}</span>
                        <div className="contact-actions">
                            <button onClick={() => onEdit(contact)}>Edit</button>
                            <button className="logout-btn" onClick={() => onDelete(contact.id)}>Delete</button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

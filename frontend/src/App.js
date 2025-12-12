const App = () => {
    const [token, setToken] = React.useState(null);
    const [contacts, setContacts] = React.useState([]);
    const [currentContact, setCurrentContact] = React.useState(null);
    const [notification, setNotification] = React.useState({ message: '', type: '' });

    const api = axios.create({
        baseURL: 'http://localhost:8082/api',
    });

    api.interceptors.request.use(config => {
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    });

    React.useEffect(() => {
        if (token) {
            fetchContacts();
        }
    }, [token]);

    const showNotification = (message, type) => {
        setNotification({ message, type });
        setTimeout(() => {
            setNotification({ message: '', type: '' });
        }, 5000);
    };

    const handleLogin = async (username, password, isRegister) => {
        try {
            if (isRegister) {
                await api.post('/users/register', { username, password });
                showNotification('Registration successful! Please log in.', 'success');
            }
            const response = await api.post('/users/login', { username, password });
            setToken(response.data.token);
            showNotification('Login successful!', 'success');
        } catch (error) {
            showNotification('Authentication failed. Please check your credentials.', 'error');
        }
    };

    const handleLogout = () => {
        setToken(null);
        setContacts([]);
        showNotification('Logged out successfully.', 'success');
    };

    const fetchContacts = async () => {
        try {
            const response = await api.get('/contacts');
            setContacts(response.data);
        } catch (error) {
            showNotification('Failed to fetch contacts.', 'error');
        }
    };

    const handleSaveContact = async (contact) => {
        try {
            if (contact.id) {
                await api.put(`/contacts/${contact.id}`, contact);
                showNotification('Contact updated successfully!', 'success');
            } else {
                await api.post('/contacts', contact);
                showNotification('Contact added successfully!', 'success');
            }
            fetchContacts();
            setCurrentContact(null);
        } catch (error) {
            showNotification('Failed to save contact.', 'error');
        }
    };

    const handleEditContact = (contact) => {
        setCurrentContact(contact);
    };

    const handleDeleteContact = async (id) => {
        try {
            await api.delete(`/contacts/${id}`);
            fetchContacts();
            showNotification('Contact deleted successfully!', 'success');
        } catch (error) {
            showNotification('Failed to delete contact.', 'error');
        }
    };

    return (
        <div>
            <Notification message={notification.message} type={notification.type} />
            <h1>Phone Book App</h1>
            {!token ? (
                <Auth onLogin={handleLogin} />
            ) : (
                <div>
                    <button className="logout-btn" onClick={handleLogout}>Logout</button>
                    <ContactForm onSave={handleSaveContact} currentContact={currentContact} />
                    <ContactList contacts={contacts} onEdit={handleEditContact} onDelete={handleDeleteContact} />
                </div>
            )}
        </div>
    );
};

ReactDOM.render(<App />, document.getElementById('root'));

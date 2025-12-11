const App = () => {
    const [token, setToken] = React.useState(null);
    const [contacts, setContacts] = React.useState([]);
    const [currentContact, setCurrentContact] = React.useState(null);

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

    const handleLogin = async (username, password, isRegister) => {
        try {
            if (isRegister) {
                await api.post('/users/register', { username, password });
            }
            const response = await api.post('/users/login', { username, password });
            setToken(response.data.token);
        } catch (error) {
            console.error('Authentication failed:', error);
        }
    };

    const handleLogout = () => {
        setToken(null);
        setContacts([]);
    };

    const fetchContacts = async () => {
        try {
            const response = await api.get('/contacts');
            setContacts(response.data);
        } catch (error) {
            console.error('Failed to fetch contacts:', error);
        }
    };

    const handleSaveContact = async (contact) => {
        try {
            if (contact.id) {
                await api.put(`/contacts/${contact.id}`, contact);
            } else {
                await api.post('/contacts', contact);
            }
            fetchContacts();
            setCurrentContact(null);
        } catch (error) {
            console.error('Failed to save contact:', error);
        }
    };

    const handleEditContact = (contact) => {
        setCurrentContact(contact);
    };

    const handleDeleteContact = async (id) => {
        try {
            await api.delete(`/contacts/${id}`);
            fetchContacts();
        } catch (error) {
            console.error('Failed to delete contact:', error);
        }
    };

    return (
        <div>
            <h1>Phone Book App</h1>
            {!token ? (
                <Auth onLogin={handleLogin} />
            ) : (
                <div>
                    <button onClick={handleLogout}>Logout</button>
                    <ContactForm onSave={handleSaveContact} currentContact={currentContact} />
                    <ContactList contacts={contacts} onEdit={handleEditContact} onDelete={handleDeleteContact} />
                </div>
            )}
        </div>
    );
};

ReactDOM.render(<App />, document.getElementById('root'));

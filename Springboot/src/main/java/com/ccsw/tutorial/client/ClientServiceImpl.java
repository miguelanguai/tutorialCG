package com.ccsw.tutorial.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

import jakarta.transaction.Transactional;

/**
 * @author mguaitav
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {

        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {

        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws {@link DataIntegrityViolationException}
     */
    @Override
    public void save(Long id, ClientDto dto) {

        Client client;

        /*
         * Aunque al principio he pensado en añadir la funcionalidad de evitar nombres
         * repetidos en el front, no sabía como hacerlo y eso no lo he visto en el
         * tutorial, por lo que aplico esto en el back. Primero se necesita validar que
         * el nombre no exista para poder ejecutar el resto de acciones. Si el nombre
         * existe, no se ejecuta el guardado. Para ello he generado otra funcion, a fin
         * de tener el codigo mas compartimentado y que, si se necesitara, fuera
         * reutilizable para otras funcionalidades.
         */

        if (!nameExists(dto)) {
            if (id == null) {
                client = new Client();
            } else {
                client = this.get(id);
            }
            client.setName(dto.getName());
            this.clientRepository.save(client);
        }
    }

    /**
     * Método para verificar si el nombre de un {@link Client} ya existe
     *
     * @param dto datos de la entidad
     * 
     * @return true si el nombre existe, false si no
     */
    public boolean nameExists(ClientDto dto) {
        boolean nameExists = false;
        List<Client> listOfClients = findAll();
        for (Client i : listOfClients) {
            if (i.getName().equals(dto.getName())) {
                // si hay alguno en el array igual al del parametro
                nameExists = true;
            }
        }
        return nameExists;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.clientRepository.deleteById(id);
    }

}
/*******************************************************************************
 * Copyright (c) 2013 Atlanmod INRIA LINA Mines Nantes
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 *******************************************************************************/
package fr.inria.atlanmod.neoemf.tests;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.inria.atlanmod.neoemf.graph.blueprints.util.NeoBlueprintsURI;
import fr.inria.atlanmod.neoemf.map.util.NeoMapURI;
import fr.inria.atlanmod.neoemf.resources.PersistentResource;
import fr.inria.atlanmod.neoemf.resources.impl.PersistentResourceFactoryImpl;
import fr.inria.atlanmod.neoemf.resources.impl.PersistentResourceImpl;
import fr.inria.atlanmod.neoemf.test.commons.models.mapSample.MapSampleFactory;
import fr.inria.atlanmod.neoemf.test.commons.models.mapSample.MapSamplePackage;
import fr.inria.atlanmod.neoemf.test.commons.models.mapSample.SampleModel;
import fr.inria.atlanmod.neoemf.test.commons.models.mapSample.SampleModelContentObject;

public class LoadedResourceResourceTest extends AllBackendTest {

    MapSampleFactory factory;
    
    @Before
    public void setUp() throws Exception {
        this.factory = MapSampleFactory.eINSTANCE;
        this.ePackage = MapSamplePackage.eINSTANCE;
        
        super.setUp();
        super.createPersistentStores();
        
        SampleModel mapSampleModel = factory.createSampleModel();
        SampleModelContentObject mapSampleContentObject = factory.createSampleModelContentObject();
        mapSampleModel.getContentObjects().add(mapSampleContentObject);
        mapResource.getContents().add(mapSampleModel);
        
        SampleModel neo4jSampleModel = factory.createSampleModel();
        SampleModelContentObject neo4jSampleContentObject = factory.createSampleModelContentObject();
        neo4jSampleModel.getContentObjects().add(neo4jSampleContentObject);
        neo4jResource.getContents().add(neo4jSampleModel);
        
        SampleModel tinkerSampleModel = factory.createSampleModel();
        SampleModelContentObject tinkerSampleContentObject = factory.createSampleModelContentObject();
        tinkerSampleModel.getContentObjects().add(tinkerSampleContentObject);
        tinkerResource.getContents().add(tinkerSampleModel);
        
        mapResource.save(Collections.EMPTY_MAP);
        neo4jResource.save(Collections.EMPTY_MAP);
        tinkerResource.save(Collections.EMPTY_MAP);
        
        PersistentResourceImpl.shutdownWithoutUnload((PersistentResourceImpl)mapResource);
        PersistentResourceImpl.shutdownWithoutUnload((PersistentResourceImpl)neo4jResource);
        PersistentResourceImpl.shutdownWithoutUnload((PersistentResourceImpl)tinkerResource);
        
        mapResource = null;
        neo4jResource = null;
        tinkerResource = null;
        
        ResourceSet rSet = new ResourceSetImpl();
        rSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put(NeoMapURI.NEO_MAP_SCHEME, new PersistentResourceFactoryImpl());
        rSet.getResourceFactoryRegistry().getProtocolToFactoryMap().put(NeoBlueprintsURI.NEO_GRAPH_SCHEME, new PersistentResourceFactoryImpl());
        mapResource = (PersistentResource)rSet.getResource(NeoMapURI.createNeoMapURI(mapFile), true);
        neo4jResource = (PersistentResource)rSet.getResource(NeoBlueprintsURI.createNeoGraphURI(neo4jFile), true);
        tinkerResource = (PersistentResource)rSet.getResource(NeoBlueprintsURI.createNeoGraphURI(tinkerFile), true);
    
        mapResource.load(Collections.EMPTY_MAP);
        neo4jResource.load(Collections.EMPTY_MAP);
        tinkerResource.load(Collections.EMPTY_MAP);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testGetElementsEResourceMapDB() {
        SampleModel model = (SampleModel)mapResource.getContents().get(0);
        assert model.eResource().equals(mapResource) : "Wrong eResource value";
        SampleModelContentObject modelContent = model.getContentObjects().get(0);
        assert modelContent.eResource().equals(mapResource) : "Wrong eResource value";
    }
    
    @Test
    public void testGetElementsEResourceNeo4j() {
        SampleModel model = (SampleModel)neo4jResource.getContents().get(0);
        assert model.eResource().equals(neo4jResource) : "Wrong eResource value";
        SampleModelContentObject modelContent = model.getContentObjects().get(0);
        assert modelContent.eResource().equals(neo4jResource) : "Wrong eResource value";
    }
    
    @Test
    public void testGetElementsEResourceTinker() {
        SampleModel model = (SampleModel)tinkerResource.getContents().get(0);
        assert model.eResource().equals(tinkerResource) : "Wrong eResource value";
        SampleModelContentObject modelContent = model.getContentObjects().get(0);
        assert modelContent.eResource().equals(tinkerResource) : "Wrong eResource value";
    }
    
    @Test
    public void testGetAllContentsEResourceMapDB() {
        Iterator<EObject> it = mapResource.getAllContents();
        EObject sampleModel = it.next();
        assert sampleModel.eResource().equals(mapResource) : "Wrong eResource value";
        EObject sampleContentObject = it.next();
        assert sampleContentObject.eResource().equals(mapResource) : "Wrong eResource value";
    }
    
    @Test
    public void testGetAllContentsEResourceNeo4j() {
        Iterator<EObject> it = neo4jResource.getAllContents();
        EObject sampleModel = it.next();
        assert sampleModel.eResource().equals(neo4jResource) : "Wrong eResource value";
        EObject sampleContentObject = it.next();
        assert sampleContentObject.eResource().equals(neo4jResource) : "Wrong eResource value";
    }
    
    @Test
    public void testGetAllContentsEResourceTinker() {
        Iterator<EObject> it = tinkerResource.getAllContents();
        EObject sampleModel = it.next();
        assert sampleModel.eResource().equals(tinkerResource) : "Wrong eResource value";
        EObject sampleContentObject = it.next();
        assert sampleContentObject.eResource().equals(tinkerResource) : "Wrong eResource value";
    }
    
    @Test
    public void testGetElementsEDirectResourceMapDB() {
        InternalEObject model = (InternalEObject)mapResource.getContents().get(0);
        assert model.eDirectResource() == null : "eDirectResource must return null";
        InternalEObject modelContent = (InternalEObject)((SampleModel)model).getContentObjects().get(0);
        assert modelContent.eDirectResource() == null : "eDirectResource must return null";
    }
    
    @Test
    public void testGetElementsEDirectResourceNeo4j() {
        InternalEObject model = (InternalEObject)neo4jResource.getContents().get(0);
        assert model.eDirectResource() == null : "eDirectResource must return null";
        InternalEObject modelContent = (InternalEObject)((SampleModel)model).getContentObjects().get(0);
        assert modelContent.eDirectResource() == null : "eDirectResource must return null";
    }
    
    @Test
    public void testGetElementsEDirectResourceTinker() {
        InternalEObject model = (InternalEObject)tinkerResource.getContents().get(0);
        assert model.eDirectResource() == null : "eDirectResource must return null";
        InternalEObject modelContent = (InternalEObject)((SampleModel)model).getContentObjects().get(0);
        assert modelContent.eDirectResource() == null : "eDirectResource must return null";
    }
    
    @Test
    public void testGetAllContentsEDirectResourceMapDB() {
        Iterator<EObject> it = mapResource.getAllContents();
        InternalEObject sampleModel = (InternalEObject)it.next();
        assert sampleModel.eDirectResource() == null : "eDirectResource must return null";
        InternalEObject sampleContentObject = (InternalEObject)it.next();
        assert sampleContentObject.eDirectResource() == null : "eDirectResource must return null";
    }
    
    @Test
    public void testGetAllContentsEDirectResourceNeo4j() {
        Iterator<EObject> it = neo4jResource.getAllContents();
        InternalEObject sampleModel = (InternalEObject)it.next();
        assert sampleModel.eDirectResource() == null : "eDirectResource must return null";
        InternalEObject sampleContentObject = (InternalEObject)it.next();
        assert sampleContentObject.eDirectResource() == null : "eDirectResource must return null";
    }
    
    @Test
    public void testGetAllContentsEDirectResourceTinker() {
        Iterator<EObject> it = tinkerResource.getAllContents();
        InternalEObject sampleModel = (InternalEObject)it.next();
        assert sampleModel.eDirectResource() == null : "eDirectResource must return null";
        InternalEObject sampleContentObject = (InternalEObject)it.next();
        assert sampleContentObject.eDirectResource() == null : "eDirectResource must return null";
    }
    
}

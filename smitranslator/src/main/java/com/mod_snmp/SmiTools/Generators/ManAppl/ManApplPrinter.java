package com.mod_snmp.SmiTools.Generators.ManAppl;
/* Copyright 2000-2013 Harrie Hazewinkel. All rights reserved.*/

import com.mod_snmp.SmiParser.ErrorHandler.ErrorException;
import com.mod_snmp.SmiParser.ErrorHandler.SmiException;
import com.mod_snmp.SmiParser.MibTree.MibTree;
import com.mod_snmp.SmiParser.MibTree.MibTreeException;
import com.mod_snmp.SmiParser.MibTree.MibTreeNode;
import com.mod_snmp.SmiParser.Semantics.ModuleHashTable;
import com.mod_snmp.SmiParser.Semantics.SymbolHashTable;
import com.mod_snmp.SmiParser.SyntaxTree.*;
import com.mod_snmp.SmiParser.Visitor.ObjectDepthFirst;

import java.io.File;
import java.io.OutputStream;

public class ManApplPrinter extends ObjectDepthFirst {
    private String packageName;
    private SymbolHashTable moduleHash;
    private String moduleName;
    protected ManApplWriter out;

    public ManApplPrinter(ModuleDefinition md) {
        this(System.out, md);
    }
    public ManApplPrinter(OutputStream o, ModuleDefinition md) {
        out = new ManApplWriter(o);
        md.accept(this, new Object());
    }
    public ManApplPrinter(String pkgName, ModuleDefinition md) {
        packageName = pkgName;
        md.accept(this, new Object());
    }
    public ManApplPrinter(ManApplWriter output) {
        out = output;
    }

    /**
     * <PRE>
     * moduleIdentifier -> ModuleIdentifier()
     * -> &lt;DEFINITIONS_T&gt;
     * -> &lt;ASSIGN_T&gt;
     * -> &lt;BEGIN_T&gt;
     * exports_list -> [ &lt;EXPORTS_T&gt; ModuleExport() &lt;SEMI_COLON_T&gt; ]
     * import_module_list -> [ &lt;IMPORTS_T&gt; ( ModuleImport() )* &lt;SEMI_COLON_T&gt; ]
     * assignmentList -> ( Assignment() )*
     * -> &lt;END_T&gt;
     * </PRE>
     */
    public Object visit(ModuleDefinition n, Object argu) {
        Object _ret=argu;

        try {
            moduleName = out.toPackage(n.moduleIdentifier);
            File dir = new File(packageName + "/" + moduleName);
            dir.mkdirs();
            moduleHash = ModuleHashTable.lookup(n.moduleIdentifier.toString());
            _ret=n.import_module_list.accept(this, _ret);
            _ret=n.assignmentList.accept(this, _ret);
        } catch (SmiException e) {
            System.out.println(e.getMessage());
        }

        //_ret=n.moduleIdentifier.accept(this, _ret);
        //_ret=n.exports_list.accept(this, _ret);
        //_ret=n.import_module_list.accept(this, _ret);
        //_ret=n.assignmentList.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * PredefinedMacro() -> (&lt;OBJECT_TYPE_T&gt;        | &lt;OBJECT_IDENTITY_T&gt;
     *                     | &lt;OBJECT_GROUP_T&gt;       | &lt;TEXTUAL_CONVENTION_T&gt;
     *                     | &lt;NOTIFICATION_TYPE_T&gt;  | &lt;NOTIFICATION_GROUP_T&gt;
     *                     | &lt;MODULE_IDENTITY_T&gt;    | &lt;MODULE_COMPLIANCE_T&gt;
     *                     | &lt;AGENT_CAPABILITIES_T&gt; | &lt;TRAP_TYPE_T&gt; )
     * SmiTypes() -> ( &lt;IP_ADDRESS_T&gt;   | &lt;NETWORK_ADDRESS_T&gt;      
     *               | &lt;NSAP_ADDRESS_T&gt; | &lt;COUNTER_T&gt;               
     *               | &lt;COUNTER32_T&gt;    | &lt;COUNTER64_T&gt;     
     *               | &lt;GAUGE_T&gt;        | &lt;GAUGE32_T&gt; 
     *               | &lt;TIMETICKS_T&gt;    | &lt;OPAQUE_T&gt;     
     *               | &lt;INTEGER_T&gt;      | &lt;INTEGER32_T&gt;   
     *               | &lt;UINTEGER32_T&gt;   | &lt;UNSIGNED32_T&gt; )      
     * Symbol() -> ( TypeIdentifier() | ValueIdentifier()      
     *       | SmiTypes() | PredefinedMacro() )      
     *
     * symbolList -> Symbol() (&lt;COMMA&gt; Symbol() )*
     * -> &lt;FROM_T&gt;
     * moduleIdentifier -> ModuleIdentifier()
     * </PRE>
     */
    public Object visit(ModuleImport n, Object argu) {
        Object _ret=argu;
        //_ret=n.symbolList.accept(this, _ret);
        //_ret=n.moduleIdentifier.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * nodeChoice -> TypeIdentifier() &lt;ASSIGN_T&gt; Types()
     * </PRE>
     */
    public Object visit(AssignmentType n, Object argu) {
        Object _ret=argu;
        //_ret=n.identifier.accept(this, _ret);
        //_ret=n.type.accept(this, _ret);
        return _ret;
    }

    /**
     * Grammar production:
     * <PRE>
     * -> &lt;SEQUENCE_T&gt;                                 
     *    &lt;L_BRACE_T&gt;
     *    ValueType() ( &lt;COMMA_T&gt; ValueType() )*
     *    &lt;R_BRACE_T&gt;          
     * vtList -> ( ValueType() )*
     * </PRE>
     */
    public Object visit(TypeSequence n, Object argu) {
        Object _ret=argu;
        //_ret=n.vtList.accept(this, _ret);
        return _ret;
    }   

   /**
     * <PRE>
     * -> &lt;TEXTUAL_CONVENTION_T&gt;
     * displayhint -> DisplayHintPart();
     * status -> StatusPart();
     * description -> DescriptionPart();
     * reference -> ReferencePart();
     * syntax -> SyntaxPart();
     * </PRE>
     */
    public Object visit(TypeTextualConvention n, Object argu) {
        Object _ret=argu;
        //_ret=n.displayhint.accept(this, _ret);
        //_ret=n.status.accept(this, _ret);
        //_ret=n.description.accept(this, _ret);
        //_ret=n.reference.accept(this, _ret);
        //_ret=n.syntax.accept(this, _ret);   
        return _ret;
    }

    /** 
     * <PRE>
     * ->  &lt;CHOICE_T&gt;
     * choice ->  ChoiceValue()
     * </PRE>
     */   
    public Object visit(TypeChoice n, Object argu) {
        Object _ret=argu;
        //_ret=n.vtList.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> [
     *     -> &lt;LEFT_SQUARE_T&gt;
     *     sort -> &lt;APPLICATION_T&gt; | &lt;UNIVERSAL_T&gt; | &lt;PRIVATE_T&gt;
     *     number -> &lt;DEC_NUMBER_T&gt;
     *     -> &lt;RIGHT_SQUARE_T&gt;
     *     kind -> [ &lt;EXPLICIT_T&gt; | &lt;IMPLICIT_T&gt; ]
     *     ]
     * type -> Types() | Asn1Types()
     * </PRE>
     */
    public Object visit(TypeTag n, Object argu) {
        Object _ret=argu;
        if (n.present()) {
            //_ret=n.sort.accept(this, _ret);
            //_ret=n.number.accept(this, _ret);
            //_ret=n.kind.accept(this, _ret);
        }
        //_ret=n.type.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * identifier -> PredefinedMacros()  smi_defined == true
     *               |
     *               TypeIdentifier()    smi_defined == false
     * -> &lt;MACRO_T&gt;
     * -> &lt;ASSIGN_T&gt;
     * -> &lt;BEGIN_T&gt;
     * macro -> MacroBody()
     * -> &lt;END_T&gt;
     * </PRE>
     */ 
    public Object visit(AssignmentMacro n, Object argu) {
        Object _ret=argu;
        //_ret=n.identifier.accept(this, _ret);
        //_ret=n.macro.accept(this, _ret);
        return _ret;
    }  
   
    /**
     * <PRE>
     * identifier -> ValueIdentifier()
     * info -> ObjectInfo()
     * -> &lt;ASSIGN_T&gt;   
     * assignedValue -> AssignedValue()
     * </PRE>
     */         
    public Object visit(AssignmentObject n, Object argu) {
        Object _ret=argu;

        try {
            MibTreeNode mtn = MibTree.symbolTable.lookup(n.identifier);
            if (mtn.isScalar() || mtn.isTable()) {
                out = ManApplWriter.create(packageName, moduleName, n.identifier);
                out.println("package " + out.stripDots(packageName) + "." + moduleName + ";");
                out.println("import com.mod_snmp.Snmp.Manager.*;");
                out.println("import com.mod_snmp.Snmp.Protocol.*;");
                if (mtn.isScalar()) {
                    _ret=n.accept(new ManApplScalarPrinter(out), _ret);
                } else if (mtn.isTable()) {
                    _ret=n.accept(new ManApplTablePrinter(out), _ret);
                }
            }
        } catch (MibTreeException exception) {
        } catch (ErrorException e) {
        }

        //_ret=n.identifier.accept(this, _ret);
        //_ret=n.info.accept(this, _ret);
        //_ret=n.assignedValue.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;OBJECT_IDENTIFIER_T&gt;
     * </PRE>
     */
    public Object visit(ObjectInfoObjectIdentifier n, Object argu) {
        Object _ret=argu;
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;OBJECT_TYPE_T&gt;
     * syntaxPart -> SyntaxPart()
     * unitsPart -> UnitsPart()
     * max_access -> AccessPart()
     * statusPart -> StatusPart()
     * descriptionPart -> DescriptionPart()
     * referencePart -> ReferencePart()
     * indexPart -> IndexPart()
     * defValPart -> DefValPart()
     * </PRE>
     */
    public Object visit(ObjectInfoObjectType n, Object argu) {
        Object _ret=argu;
        //_ret=n.syntaxPart.accept(this, _ret);
        //_ret=n.unitsPart.accept(this, _ret);
        //_ret=n.max_access.accept(this, _ret);
        //_ret=n.statusPart.accept(this, _ret);
        _ret=n.descriptionPart.accept(this, _ret);
        //_ret=n.referencePart.accept(this, _ret);
        //_ret=n.indexPart.accept(this, _ret);
        //_ret=n.defValPart.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> Arbitrary not parsed stuff
     * </PRE>
     */
    public Object visit(MacroBody n, Object argu) {
        Object _ret=null;
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;MODULE_IDENTITY_T&gt;
     * lastUpdatedPart -> LastUpdatedPart()
     * organizationPart -> OrganizationPart()
     * contactInfoPart -> ContactInfoPart()
     * descriptionPart -> DescriptionPart()
     * revisions -> RevisionPart()
     * </PRE>
     */
    public Object visit(ObjectInfoModuleIdentity n, Object argu) {
        Object _ret=argu;
        //_ret=n.lastUpdatedPart.accept(this, _ret);
        //_ret=n.organizationPart.accept(this, _ret);
        //_ret=n.contactInfoPart.accept(this, _ret);
        //_ret=n.descriptionPart.accept(this, _ret);
        //_ret=n.revisions.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;OBJECT_IDENTITY_T&gt;
     * statusPart -> StatusPart()
     * descriptionPart -> DescriptionPart()
     * referencePart -> ReferencePart()
     * </PRE>
     */
    public Object visit(ObjectInfoObjectIdentity n, Object argu) {
        Object _ret=argu;
        //_ret=n.statusPart.accept(this, _ret);
        //_ret=n.descriptionPart.accept(this, _ret);
        //_ret=n.referencePart.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;OBJECT_GROUP_T&gt;
     * objectsPart -> ObjectsPart()
     *             -> [ &lt;OBJECTS_T&gt; &lt;LEFT_BRACE_T&gt;
     *                  ObjectList()
     *                  &lt;RIGHT_BRACE_T&gt; ]
     * statusPart -> StatusPart()
     * descriptionPart -> DescriptionPart()
     * referencePart -> ReferencePart()
     * </PRE>
     */
    public Object visit(ObjectInfoObjectGroup n, Object argu) {
        Object _ret=argu;
        //_ret=n.objectsPart.accept(this, _ret);
        //_ret=n.statusPart.accept(this, _ret);
        //_ret=n.descriptionPart.accept(this, _ret);
        //_ret=n.referencePart.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;NOTIFICATION_TYPE_T&gt;
     * objectsPart -> ObjectsPart()
     *             -> [ &lt;OBJECTS_T&gt; &lt;LEFT_BRACE_T&gt;
     *                  ObjectList()
     *                  &lt;RIGHT_BRACE_T&gt; ]
     * statusPart -> StatusPart()
     * descriptionPart -> DescriptionPart()
     * referencePart -> ReferencePart()
     * </PRE>
     */
    public Object visit(ObjectInfoNotificationType n, Object argu) {
        Object _ret=argu;
        //_ret=n.objectsPart.accept(this, _ret);
        //_ret=n.statusPart.accept(this, _ret);
        //_ret=n.descriptionPart.accept(this, _ret);
        //_ret=n.referencePart.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;NOTIFICATION_GROUP_T&gt;
     * notifications -> NotificationPart()
     *               -> &lt;NOTIFICATIONS_T&gt;
     *                  &lt;LEFT_BRACE_T&gt;   
     *                  ObjectList()
     *                  &lt;RIGHT_BRACE_T&gt;
     * statusPart -> StatusPart()
     * descriptionPart -> DescriptionPart()
     * referencePart -> ReferencePart()
     * </PRE>
     */
    public Object visit(ObjectInfoNotificationGroup n, Object argu) {
        Object _ret=argu;
        //_ret=n.notifications.accept(this, _ret);
        //_ret=n.statusPart.accept(this, _ret);
        //_ret=n.descriptionPart.accept(this, _ret);
        //_ret=n.referencePart.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;MODULE_COMPLIANCE_T&gt;
     * statusPart -> StatusPart()
     * descriptionPart -> DescriptionPart()
     * referencePart -> ReferencePart()
     * moduleCompliancePart -> ( &lt;MODULE_T&gt; [ ModuleIdentifier() ]
     *                           [ MandatoryPart() ]
     *                           ( CompliancePart() )*
     *                          )+ 
     * </PRE>
     */
    public Object visit(ObjectInfoModuleCompliance n, Object argu) {
        Object _ret=argu;
        //_ret=n.statusPart.accept(this, _ret);
        //_ret=n.descriptionPart.accept(this, _ret);
        //_ret=n.referencePart.accept(this, _ret);
        //_ret=n.moduleCompliancePart.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;AGENT_CAPABILITIES_T&gt;
     * productReleasePart -> ProductReleasePart()
     * statusPart -> StatusPart()
     * descriptionPart -> DescriptionPart()
     * referencePart -> ReferencePart()
     * moduleCapabilitiesPart -> ( ModuleCapability() )*
     * </PRE>
     */
    public Object visit(ObjectInfoAgentCapabilities n, Object argu) {
        Object _ret=argu;
        //_ret=n.productReleasePart.accept(this, _ret);
        //_ret=n.statusPart.accept(this, _ret);
        //_ret=n.descriptionPart.accept(this, _ret);
        //_ret=n.referencePart.accept(this, _ret);
        //_ret=n.moduleCapabilitiesPart.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;TRAP_TYPE_T&gt;
     * enterprisePart -> EnterprisePart()
     * variablesPart -> VariablesPart()
     *               -> [ &lt;VARIABLES_T&gt;
     *                    &lt;LEFT_BRACE_T&gt;
     *                    ObjectList()
     *                    &lt;RIGHT_BRACE_T&gt; ]
     * descriptionPart -> DescriptionPart()
     * number -> NumericValue()
     * </PRE>
     */
    public Object visit(ObjectInfoTrapType n, Object argu) {
        Object _ret=argu;
        //_ret=n.enterprisePart.accept(this, _ret);
        //_ret=n.variablesPart.accept(this, _ret);
        //_ret=n.descriptionPart.accept(this, _ret);
        //_ret=n.number.accept(this, _ret);
        return _ret;
    }

    /**
     * Grammar production:
     * <PRE>
     * nodeList -> &lt;REVISION_T&gt; &lt;UTC_TIME_T&gt; DescriptionPart()     
     * </PRE>
     * Syntaxtree :
     * <PRE>
     * time -> &lt;UTC_TIME_T&gt;
     * descr -> DescriptionPart()   
     * </PRE>
     */
    public Object visit(Revision n, Object argu) {    
        Object _ret=argu;
        //_ret=n.time.accept(this, _ret);
        //_ret=n.descr.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;STATUS_T&gt;
     * nodeChoice -> ( &lt;CURRENT_T&gt; | &lt;DEPRECATED_T&gt; | &lt;OBSOLETE_T&gt; | &lt;MANDATORY_T&gt; )
     * </PRE>
     */
    public Object visit(StatusPart n, Object argu) {
        Object _ret=argu;
        //_ret=n.value.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * nodeChoice -> ( &lt;ACCESS_T&gt; | &lt;MAX_ACCESS_T&gt; | &lt;MIN_ACCESS_T&gt; )
     * nodeChoice1 -> ( &lt;NOT_ACCESSIBLE_T&gt; | &lt;ACCESSIBLE_FOR_NOTIFY_T&gt; | &lt;READ_ONLY_T&gt; | &lt;READ_WRITE_T&gt; | &lt;READ_CREATE_T&gt; | &lt;WRITE_ONLY_T&gt; | &lt;NOT_IMPLEMENTED_T&gt; )
     * </PRE>
     */
    public Object visit(AccessPart n, Object argu) {
        Object _ret=argu;
        //_ret=n.key.accept(this, _ret);
        //_ret=n.value.accept(this, _ret);
        return _ret;
    }

    /**
     * Grammar production:
     * <PRE>
     * -> &lt;MODULE_T&gt;
     *    [ ModuleIdentifier() ]          
     *    [ &lt;MANDATORY_GROUPS_T&gt;                         
     *      &lt;LEFT_BRACE_T&gt;               
     *      ObjectList()               
     *      &lt;RIGHT_BRACE_T&gt;
     *    ]
     *   ( CompliancePart() )*                 
     * </PRE>
     * syntax class variables:
     * <PRE>
     * module_id -> [ ModuleIdentifier() ]
     * mandatory_part -> [ ObjectList() ]                            
     * compliance_part -> ( CompliancePart() )*
     * </PRE>
     */
    public Object visit(ModuleCompliance n, Object argu) {
        Object _ret=argu;
        //_ret=n.module_id.accept(this, _ret);
        //_ret=n.mandatory_part.accept(this, _ret);
        //_ret=n.compliance_part.accept(this, _ret);
        return _ret;
    }

    /**
     * Grammar production:
     * <PRE>
     *  -> &lt;SUPPORTS_T&gt;
     * module_id -> ModuleIdentifier()
     * -> &lt;INCLUDES_T&gt;
     * -> &lt;LEFT_BRACE_T&gt;
     * object_list -> ObjectList()
     * -> &lt;RIGHT_BRACE_T&gt;  
     * variation_part -> ( VariationPart() )*
     * </PRE>
     */
    public Object visit(ModuleCapability n, Object argu) {
        Object _ret=argu;
        //_ret=n.module_id.accept(this, _ret);
        //_ret=n.object_list.accept(this, _ret);
        //_ret=n.variation_part.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;VARIATION_T&gt;
     * identifier -> ValueIdentifier()
     * syntaxPart -> SyntaxPart()
     * write_syntax -> [ &lt;WRITE_SYNTAX_T&gt; Types() ]
     * access -> [ AccessPart() ]
     * creation_requires -> [ &lt;CREATION_REQUIRES_T&gt; &lt;LEFT_BRACE_T&gt; ObjectList() &lt;RIGHT_BRACE_T&gt; ]
     * defValPart -> DefValPart()
     * descriptionPart -> DescriptionPart()
     * </PRE>
     */
    public Object visit(VariationPart n, Object argu) {
        Object _ret=argu;
        //_ret=n.identifier.accept(this, _ret);
        //_ret=n.syntaxPart.accept(this, _ret);
        //_ret=n.write_syntax.accept(this, _ret);
        //_ret=n.access.accept(this, _ret);
        //_ret=n.creation_requires.accept(this, _ret);
        //_ret=n.defValPart.accept(this, _ret);
        //_ret=n.descriptionPart.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;SYNTAX_T&gt;
     * type -> Types()
     * </PRE>
     */
    public Object visit(SyntaxPart n, Object argu) {
        Object _ret=argu;
        //_ret=n.type.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;WRITE_SYNTAX_T&gt;
     * type -> Types()
     * </PRE>
     */
    public Object visit(WriteSyntaxPart n, Object argu) {
        Object _ret=argu;
        //_ret=n.type.accept(this, _ret);
        return _ret;
    }


    /**
     * <PRE>
     * -> &lt;GROUP_T&gt; ValueIdentifier() DescriptionPart()
     * identifier -> ValueIdentifier()
     * description -> DescriptionPart()
     * </PRE>
     */
    public Object visit(ComplianceGroup n, Object argu) {
        Object _ret=argu;
        //_ret=n.identifier.accept(this, _ret);
        //_ret=n.description.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;OBJECT_T&gt; ValueIdentifier()   
     *       [ SyntaxPart() ]
     *       [ WriteSyntaxPart() ]
     *       [ AccessPart() ]
     *       DescriptionPart()
     * identifier -> ValueIdentifier()
     * syntax -> [ SyntaxPart() ]  
     * write_syntax -> [ WriteSyntaxPart() ]
     * access -> [ AccessPart() ] 
     * description -> DescriptionPart()
     * </PRE>
     */
    public Object visit(ComplianceObject n, Object argu) {
        Object _ret=argu;
        //_ret=n.identifier.accept(this, _ret);
        //_ret=n.syntax.accept(this, _ret);
        //_ret=n.write_syntax.accept(this, _ret);
        //_ret=n.min_access.accept(this, _ret);
        //_ret=n.description.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * nodeChoice -> &lt;SEQUENCE_T&gt; &lt;LEFT_BRACE_T&gt; ValueIdentifier() Types() ( &lt;COMMA_T&gt; ValueIdentifier() Types() )* &lt;RIGHT_BRACE_T&gt;
     *       | &lt;SEQUENCE_T&gt; TypeIdentifier()
     *       | &lt;SEQUENCE_OF_T&gt; TypeIdentifier()
     *       | &lt;INTEGER_T&gt; [ RestrictionInteger() ]
     *       | &lt;INTEGER32_T&gt; [ RestrictionInteger() ]
     *       | &lt;UINTEGER32_T&gt; [ RestrictionInteger() ]
     *       | &lt;UNSIGNED32_T&gt; [ RestrictionInteger() ]
     *       | &lt;BITS_T&gt; [ NamedNumberList() ]
     *       | &lt;BIT_STRING_T&gt; [ NamedNumberList() ]
     *       | &lt;OCTET_STRING_T&gt; [ RestrictionSize() ]
     *       | &lt;OBJECT_IDENTIFIER_T&gt;
     *       | &lt;COUNTER_T&gt;
     *       | &lt;COUNTER32_T&gt;
     *       | &lt;COUNTER64_T&gt;
     *       | &lt;GAUGE_T&gt; [ RestrictionInteger() ]
     *       | &lt;GAUGE32_T&gt; [ RestrictionInteger() ]
     *       | &lt;TIMETICKS_T&gt;
     *       | &lt;OPAQUE_T&gt; [ RestrictionSize() ]
     *       | &lt;NSAP_ADDRESS_T&gt;
     *       | &lt;IP_ADDRESS_T&gt;
     *       | &lt;NETWORK_ADDRESS_T&gt;
     * </PRE>
     */
    public Object visit(TypeSmi n, Object argu) {
        Object _ret=argu;
        //_ret=n.base.accept(this, _ret);
        //_ret=n.restriction.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     *       | TypeIdentifier() [ Restriction() ]
     * </PRE>
     */
    public Object visit(TypeIdentifier n, Object argu) {
        Object _ret=argu; 
        //_ret=n.identifier.accept(this, _ret);
        //_ret=n.restriction.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;SEQUENCE_OF_T&gt;
     * identifier -> TypeIdentifier()
     * </PRE>
     */
    public Object visit(TypeSequenceOf n, Object argu) {
        Object _ret=argu; 
        //_ret=n.identifier.accept(this, _ret);
        return _ret;
    }   

    /**
     * <PRE>
     * -> &lt;LEFT_PAREN_T&gt;
     * range -> Range()
     * -> &lt;RIGHT_PAREN_T&gt;
     * </PRE>
     */
    public Object visit(RestrictionRange n, Object argu) {
        Object _ret=argu;
        //_ret=n.range.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;LEFT_PAREN_T&gt;
     * -> &lt;SIZE_T&gt;
     * -> &lt;LEFT_PAREN_T&gt;
     * range -> Range()
     * -> &lt;RIGHT_PAREN_T&gt;
     * -> &lt;RIGHT_PAREN_T&gt;
     * </PRE>
     */
    public Object visit(RestrictionSize n, Object argu) {
        Object _ret=argu;
        //_ret=n.range.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;LEFT_BRACE_T&gt; 
     *            NamedNumber()
     *            ( &lt;COMMA_T&gt; NamedNumber() )*
     *    &lt;RIGHT_BRACE_T&gt;
     * list -> ( NamedNumber() )+
     * </PRE>
     */
    public Object visit(RestrictionNamedNumberList n, Object argu) {
        Object _ret=argu;
        //_ret=n.list.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * list-> RangeItem() ( &lt;OR_T&gt; RangeItem() )*
     * </PRE>
     */
    public Object visit(RangeList n, Object argu) {
        Object _ret=argu;
        //_ret=n.list.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> NumericValue()
     *    [ &lt;UNTIL_T&gt; NumericValue() ]
     * begin -> NumericValue()
     * end -> NumericValue() -- NodeOptional --
     * </PRE>
     */ 
    public Object visit(RangeItem n, Object argu) {
        Object _ret=argu;
        //_ret=n.begin.accept(this, _ret);
        //_ret=n.end.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * identifier -> ValueIdentifier()
     * -> &lt;LEFT_PAREN_T&gt;
     * number -> NumericValue()
     * -> &lt;RIGHT_PAREN_T&gt;
     * </PRE>
     */
    public Object visit(NamedNumber n, Object argu) {
        Object _ret=argu;
        //_ret=n.name.accept(this, _ret);
        //_ret=n.number.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * nodeChoice -> ( ModuleIdentifier() &lt;DOT_T&gt; &lt;TYPE_IDENTIFIER_T&gt; | &lt;TYPE_IDENTIFIER_T&gt; )
     * nodeChoice -> ( ModuleIdentifier() &lt;DOT_T&gt; ( &lt;VALUE_IDENTIFIER_T&gt; | &lt;NOT_IMPLEMENTED_T&gt; | &lt;CURRENT_T&gt; | &lt;DEPRECATED_T&gt; | &lt;OBSOLETE_T&gt; | &lt;MANDATORY_T&gt; ) | ( &lt;VALUE_IDENTIFIER_T&gt; | &lt;NOT_IMPLEMENTED_T&gt; | &lt;CURRENT_T&gt; | &lt;DEPRECATED_T&gt; | &lt;OBSOLETE_T&gt; | &lt;MANDATORY_T&gt; ) )
     * </PRE>
     */
    public Object visit(Identifier n, Object argu) {
        Object _ret=argu;
        if (n.module_identifier != null) {
            //_ret=n.module_identifier.accept(this, _ret);
        }
        //_ret=n.identifier.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;TYPE_IDENTIFIER_T&gt;
     * </PRE>
     */
    public Object visit(ModuleIdentifier n, Object argu) {
        Object _ret=argu;
        //_ret=n.nodeToken.accept(this, _ret);
        return _ret;
    }

    /**
     * Grammar production:
     * <PRE>
     * -> &lt;AUGMENTS_T&gt;
     * -> &lt;LEFT_BRACE_T&gt;
     * identifier -> Identifier()
     * -> &lt;RIGHT_BRACE_T&gt; )
     * </PRE>
     */
    public Object visit(IndexAugments n, Object argu) {
        Object _ret=argu;
        //_ret=n.identifier.accept(this, _ret);
        return _ret;
    }

    /**
     * Grammar production:
     * <PRE>
     * -> &lt;IMPLIED_T&gt;
     * identifier -> Identifier()
     * </PRE>
     */
    public Object visit(Index n, Object argu) {
        Object _ret=argu;
        //_ret=n.identifier.accept(this, _ret);
        return _ret;
    }
 
    /**
     * <PRE>
     * -> [ ( &lt;INDEX_T&gt; &lt;LEFT_BRACE_T&gt; IndexTypes() &lt;RIGHT_BRACE_T&gt; ]
     * list -> Index() ( &lt;COMMA_T&gt; Index() )*
     * </PRE>
     */
    public Object visit(IndexList n, Object argu) {
        Object _ret=argu;
        //_ret=n.list.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;LEFT_BRACE_T&gt;
     * oidValue -> OidValue()
     * nodeList -> ( OidValue() )+
     * -> &lt;RIGHT_BRACE_T&gt;
     * </PRE>
     */
    public Object visit(AssignedValue n, Object argu) {
        Object _ret=argu;
        //_ret=n.oidValue.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * nodeChoice -> ValueIdentifier() [ &lt;LEFT_PAREN_T&gt; NumericValue() &lt;RIGHT_PAREN_T&gt; ]
     *       | NumericValue()
     * </PRE>
     */
    public Object visit(OidValue n, Object argu) {
        Object _ret=argu;
        if (n.name != null) {
            //_ret=n.name.accept(this, _ret);
            if (n.numval != null) {
                //_ret=n.numval.accept(this, _ret);
            }
        } else if (n.numval != null) {
            //_ret=n.numval.accept(this, _ret);    
        }   
        return _ret;
    }

    /**
     * <PRE>
     * nodeChoice -> &lt;DEC_NUMBER_T&gt;
     *       | &lt;BIN_NUMBER_T&gt;
     *       | &lt;HEX_NUMBER_T&gt;
     * </PRE>
     */
    public Object visit(NumericValue n, Object argu) {
        Object _ret=argu;
        //_ret=n.value.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;DESCRIPTION_T&gt;
     * nodeToken1 -> &lt;TEXT_T&gt;
     * </PRE>
     */
    public Object visit(DescriptionPart n, Object argu) {
        Object _ret=argu;
        if (argu instanceof Integer) {
            int indentLevel = ((Integer)argu).intValue();
            out.println(indentLevel, " * <P>");
            out.printStripped(indentLevel, n.descr.toString());
            out.println(indentLevel, " * </P>");
        }

        //_ret=n.descr.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * nodeOptional -> [ &lt;REFERENCE_T&gt; &lt;TEXT_T&gt; ]
     * </PRE>
     */
    public Object visit(ReferencePart n, Object argu) {
        Object _ret=argu;

        if (argu instanceof Integer) {
            int indentLevel = ((Integer)argu).intValue();
            out.println(indentLevel, " * Reference");
            out.printStripped(indentLevel, n.nodeOptional.toString());
        }

        //_ret=n.nodeOptional.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;CONTACT_INFO_T&gt;
     * nodeToken1 -> &lt;TEXT_T&gt;
     * </PRE>
     */
    public Object visit(ContactInfoPart n, Object argu) {
        Object _ret=argu;
        //_ret=n.nodeToken.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;ORGANIZATION_T&gt;
     * nodeToken1 -> &lt;TEXT_T&gt;
     * </PRE>
     */
    public Object visit(OrganizationPart n, Object argu) {
        Object _ret=argu;
        //_ret=n.nodeToken.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * value -> ( &lt;DEFVAL_T&gt; &lt;LEFT_BRACE_T&gt;
     *               (  NumericValue() 
     *               | ValueIdentifier()
     *               | &lt;TEXT_T&gt;
     *               | BitValueList()
     *               | AssignedValue()
     *               )
     *            &lt;RIGHT_BRACE_T&gt; )?
     * </PRE>
     */
    public Object visit(DefValPart n, Object argu) {
        Object _ret=argu;
        //_ret=n.nodeOptional.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * list -> ( ValueIdentifier() )*
     * </PRE>
     */
    public Object visit(BitValueList n, Object argu) {
        Object _ret=argu;
        //_ret=n.list.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * nodeOptional -> [ &lt;DISPLAY_HINT_T&gt; &lt;TEXT_T&gt; ]
     * </PRE>
     */
    public Object visit(DisplayHintPart n, Object argu) {
        Object _ret=argu;
        if (n.present()) {
            //_ret=n.display_hint.accept(this, _ret);
        }
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;LAST_UPDATED_T&gt;
     * nodeToken1 -> &lt;UTC_TIME_T&gt;
     * </PRE>
     */
    public Object visit(LastUpdatedPart n, Object argu) {
        Object _ret=argu;
        //_ret=n.nodeToken.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * nodeOptional -> [ &lt;UNITS_T&gt; &lt;TEXT_T&gt; ]
     * </PRE>
     */
    public Object visit(UnitsPart n, Object argu) {
        Object _ret=argu;
        //_ret=n.nodeOptional.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;PRODUCT_RELEASE_T&gt;
     * nodeToken1 -> &lt;TEXT_T&gt;
     * </PRE>
     */
    public Object visit(ProductReleasePart n, Object argu) {
        Object _ret=argu;
        //_ret=n.nodeToken.accept(this, _ret);
        return _ret;
    }

    /**
     * <PRE>
     * -> &lt;ENTERPRISE_T&gt;
     * identifier -> ValueIdentifier()
     * </PRE>
     */
    public Object visit(EnterprisePart n, Object argu) {
        Object _ret=argu;
        //_ret=n.identifier.accept(this, _ret);
        return _ret;
    }

    /**
     * Grammar production:
     * <PRE>
     * identifier -> ValueIdentifier()
     * type -> Types()
     * </PRE>
     */
    public Object visit(ValueType n, Object argu) {
        Object _ret=argu;
        //_ret=n.identifier.accept(this, _ret);
        //_ret=n.type.accept(this, _ret); 
        return _ret;
    }

    /**
     * Function 'isAccessible' provides a boolean value whether
     * the object is accessable from a MIB module definition view.
     */
    protected boolean isAccessible(AccessPart access) {
        return !(access.value.kind == AccessPart.NOT_ACCESSIBLE);
    }

    /**
     * Function 'isWritable' provides a boolean value whether
     * the object is writable from a MIB module definition view.
     */
    protected boolean isWritable(AccessPart access) {
        switch (access.value.kind) {
        case AccessPart.READ_WRITE:
        case AccessPart.READ_CREATE:
        case AccessPart.WRITE_ONLY:
            return true;
        }
        return false;
    }
    /**
     * Mediator function sitting between the grammar tokens (kind)
     * and the SMASH generic types.
     */
    protected String get_type(ObjectInfoObjectType info) {
        try {
            TypeSmi tp = (TypeSmi)info.syntaxPart.getGenericType();
            switch (((NodeToken)tp.base).kind) {
            case TypeSmi.INTEGER:
            case TypeSmi.INTEGER32:
            case TypeSmi.UINTEGER32:
            case TypeSmi.UNSIGNED32:
            case TypeSmi.COUNTER:
            case TypeSmi.COUNTER32:
            case TypeSmi.GAUGE:
            case TypeSmi.GAUGE32:
            case TypeSmi.TIMETICKS:
                return "int";
            case TypeSmi.BITS:
            case TypeSmi.BIT_STRING:
            case TypeSmi.OCTET_STRING:
            case TypeSmi.OPAQUE:
            case TypeSmi.NSAP_ADDRESS:
            case TypeSmi.IP_ADDRESS:
            case TypeSmi.NETWORK_ADDRESS:
                return "String";
            case TypeSmi.OBJECT_IDENTIFIER:
                return "String";
            case TypeSmi.COUNTER64:
                return "long";
            }
        } catch (ClassCastException exception) {
        } catch (NullPointerException exception) { System.err.println(info.syntaxPart.type.toString());
        }
        return "-";
    }

}

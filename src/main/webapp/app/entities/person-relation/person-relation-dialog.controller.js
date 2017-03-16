(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonRelationDialogController', PersonRelationDialogController);

    PersonRelationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonRelation', 'Person'];

    function PersonRelationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonRelation, Person) {
        var vm = this;

        vm.personRelation = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personRelation.id !== null) {
                PersonRelation.update(vm.personRelation, onSaveSuccess, onSaveError);
            } else {
                PersonRelation.save(vm.personRelation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personRelationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

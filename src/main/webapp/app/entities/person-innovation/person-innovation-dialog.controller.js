(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonInnovationDialogController', PersonInnovationDialogController);

    PersonInnovationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonInnovation', 'Person', 'InnovationType'];

    function PersonInnovationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonInnovation, Person, InnovationType) {
        var vm = this;

        vm.personInnovation = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query();
        vm.innovationtypes = InnovationType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personInnovation.id !== null) {
                PersonInnovation.update(vm.personInnovation, onSaveSuccess, onSaveError);
            } else {
                PersonInnovation.save(vm.personInnovation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personInnovationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonTieBaDialogController', PersonTieBaDialogController);

    PersonTieBaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonTieBa', 'Person'];

    function PersonTieBaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonTieBa, Person) {
        var vm = this;

        vm.personTieBa = entity;
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
            if (vm.personTieBa.id !== null) {
                PersonTieBa.update(vm.personTieBa, onSaveSuccess, onSaveError);
            } else {
                PersonTieBa.save(vm.personTieBa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personTieBaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

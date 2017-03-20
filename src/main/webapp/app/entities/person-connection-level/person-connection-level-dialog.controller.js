(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonConnectionLevelDialogController', PersonConnectionLevelDialogController);

    PersonConnectionLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonConnectionLevel', 'Person', 'ConnectionLevel'];

    function PersonConnectionLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonConnectionLevel, Person, ConnectionLevel) {
        var vm = this;

        vm.personConnectionLevel = entity;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query();
        vm.connectionlevels = ConnectionLevel.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personConnectionLevel.id !== null) {
                PersonConnectionLevel.update(vm.personConnectionLevel, onSaveSuccess, onSaveError);
            } else {
                PersonConnectionLevel.save(vm.personConnectionLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personConnectionLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

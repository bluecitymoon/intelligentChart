(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonFanSupportGroupDialogController', PersonFanSupportGroupDialogController);

    PersonFanSupportGroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonFanSupportGroup', 'Person'];

    function PersonFanSupportGroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonFanSupportGroup, Person) {
        var vm = this;

        vm.personFanSupportGroup = entity;
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
            if (vm.personFanSupportGroup.id !== null) {
                PersonFanSupportGroup.update(vm.personFanSupportGroup, onSaveSuccess, onSaveError);
            } else {
                PersonFanSupportGroup.save(vm.personFanSupportGroup, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personFanSupportGroupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

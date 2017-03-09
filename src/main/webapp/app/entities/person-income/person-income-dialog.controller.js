(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonIncomeDialogController', PersonIncomeDialogController);

    PersonIncomeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonIncome', 'Person'];

    function PersonIncomeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonIncome, Person) {
        var vm = this;

        vm.personIncome = entity;
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
            if (vm.personIncome.id !== null) {
                PersonIncome.update(vm.personIncome, onSaveSuccess, onSaveError);
            } else {
                PersonIncome.save(vm.personIncome, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personIncomeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

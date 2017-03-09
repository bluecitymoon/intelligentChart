(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonPaidNetworkDebit', PersonPaidNetworkDebit);

    PersonPaidNetworkDebit.$inject = ['$resource', 'DateUtils'];

    function PersonPaidNetworkDebit ($resource, DateUtils) {
        var resourceUrl =  'api/person-paid-network-debits/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertLocalDateFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    return angular.toJson(copy);
                }
            },
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-paid-network-debits/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();

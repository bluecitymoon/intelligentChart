(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonEndorsement', PersonEndorsement);

    PersonEndorsement.$inject = ['$resource', 'DateUtils'];

    function PersonEndorsement ($resource, DateUtils) {
        var resourceUrl =  'api/person-endorsements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: 'api/person-endorsements/person/:id',
                params: { id : '@id'}
            }
        });
    }
})();
